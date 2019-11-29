package com.rpc.netty.invoke;

import com.rpc.netty.RpcInvoker;
import com.rpc.support.RpcResponse;
import com.rpc.support.RpcFuture;
import io.netty.channel.Channel;

/**
 * RpcResponse响应处理器
 * @author: zhoum
 * @Date: 2019-11-26
 * @Time: 11:50
 */
public class RpcResponseInvoker implements RpcInvoker {

    @Override
    public void handle(Channel channel , Object object) {
        if ( object instanceof RpcResponse ) {
            RpcResponse resp = (RpcResponse) object;
            //处理响应数据
            RpcFuture.receive(resp);
        }
    }

}
