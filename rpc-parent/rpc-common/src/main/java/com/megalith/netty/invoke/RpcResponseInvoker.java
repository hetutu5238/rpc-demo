package com.megalith.netty.invoke;

import com.megalith.netty.RpcInvoker;
import com.megalith.support.RpcResponse;
import com.megalith.support.RpcFuture;
import io.netty.channel.Channel;

/**
 * @author: zhoum
 * @Date: 2019-11-26
 * @Time: 11:50
 */
public class RpcResponseInvoker implements RpcInvoker {

    @Override
    public void handle(Channel channel , Object object) {
        if ( object instanceof RpcResponse ) {
            RpcResponse resp = (RpcResponse) object;
            RpcFuture.receive(resp);
        }
    }

}
