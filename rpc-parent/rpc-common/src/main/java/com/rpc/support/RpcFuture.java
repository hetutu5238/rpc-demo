package com.rpc.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-28
 * @Time: 10:18
 */
public class RpcFuture {
    //存储所有已发送 但还未得到响应的请求
    private static final Map<Long, RpcFuture> REPO = new ConcurrentHashMap<>();
    //每次请求对应的锁
    private final Object lock = new Object();
    //超时时间
    private final int timeOut = 10;
    //任务id
    private long id;
    //请求内容
    private RpcRequest rpcRequest;
    //请求对应的相应内容
    private volatile RpcResponse rpcResponse;

    public RpcFuture(long id , RpcRequest rpcRequest) {
        this.id = id;
        this.rpcRequest = rpcRequest;
    }

    public static void receive(RpcResponse resp) {
        RpcFuture remove = REPO.remove(resp.getId());
        remove.doRecieve(resp);

    }

    public static void putFuture(Long id , RpcFuture rpcFuture) {
        REPO.put(id , rpcFuture);
    }

    private boolean done() {
        return this.rpcResponse != null;
    }

    private void doRecieve(RpcResponse resp) {
        synchronized (lock) {
            this.rpcResponse = resp;
            //唤醒请求时的等待
            lock.notifyAll();
        }
    }

    public Object getResponse() {
        long millis = System.currentTimeMillis();
        //也可以使用BlockingQueue代替锁  但是会使线程阻塞
        synchronized (lock) {
            if ( !done() ) {
                try {
                    while ( !done() ) {
                        lock.wait(timeOut * 1000);
                        //被唤醒后判断下完成或者超时
                        if ( done() || System.currentTimeMillis() - millis > timeOut * 1000 ) {
                            break;
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                if ( !done() ) {
                    throw new RuntimeException("服务已超时 服务id：" + id + " 服务内容:" + rpcRequest);
                }
            }
        }

        return rpcResponse.getResponse();
    }
}
