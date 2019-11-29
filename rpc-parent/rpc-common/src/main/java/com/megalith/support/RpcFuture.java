package com.megalith.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-28
 * @Time: 10:18
 */
public class RpcFuture {

    private static final Map<Long, RpcFuture> REPO = new ConcurrentHashMap<>();
    private final Object lock = new Object();
    private final int timeOut = 10;
    private long id;
    private RpcRequest rpcRequest;
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
