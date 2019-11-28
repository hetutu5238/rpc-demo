package com.megalith.support;

import com.megalith.entity.RpcRequest;
import com.megalith.entity.RpcResponse;

import javax.xml.ws.Response;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-28
 * @Time: 10:18
 */
public class RpcFuture {

    private static final Map<Long,RpcFuture> REPO = new ConcurrentHashMap<>();

    private long id;

    private RpcRequest rpcRequest;

    private volatile RpcResponse rpcResponse;

    private final Object lock = new Object();

    private final int timeOut = 10;

    private boolean done(){
        return this.rpcResponse!=null;
    }

    public RpcFuture(long id , RpcRequest rpcRequest) {
        this.id = id;
        this.rpcRequest = rpcRequest;
    }

    public static void receive(RpcResponse resp) {
        RpcFuture remove = REPO.remove(resp.getId());
        remove.doRecieve(resp);

    }

    private void doRecieve(RpcResponse resp){
        synchronized (lock){
            this.rpcResponse = resp;
            lock.notifyAll();
        }
    }

    public Object getResponse(){
        long millis = System.currentTimeMillis();
        //也可以使用BlockingQueue代替锁  但是会使线程阻塞
        synchronized (lock){
            if ( !done() ){
                try {
                    while ( !done() ){
                        lock.wait(timeOut*1000);
                        //被唤醒后判断下完成或者超时
                        if ( done() || System.currentTimeMillis()-millis > timeOut*1000){
                            break;
                        }
                    }
                }catch (Exception e){
                    throw new RuntimeException(e);
                }
                if ( !done() ){
                    throw new RuntimeException("服务已超时 服务id："+id+" 服务内容:"+rpcRequest);
                }
            }
        }

        return rpcResponse.getResponse();
    }
    public static void putFuture(Long id, RpcFuture rpcFuture) {
        REPO.put(id,rpcFuture);
    }
}
