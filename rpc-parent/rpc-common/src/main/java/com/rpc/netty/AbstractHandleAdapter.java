package com.rpc.netty;

import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-29
 * @Time: 9:39
 */
public abstract class AbstractHandleAdapter extends ChannelInboundHandlerAdapter {

    protected RpcInvoker rpcInvoker;

    public AbstractHandleAdapter(RpcInvoker rpcInvoker) {
        this.rpcInvoker = rpcInvoker;
    }
}
