package com.rpc.netty;

import io.netty.channel.ChannelHandlerContext;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-14
 * @Time: 11:40
 */
public class ChannelServerMessageHandler extends AbstractHandleAdapter {


    public ChannelServerMessageHandler(RpcInvoker rpcInvoker) {
        super(rpcInvoker);
    }

    /**
     * 有新连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String address = ctx.channel().remoteAddress().toString();
        System.out.println("收到信息: " + address);
        super.channelActive(ctx);
    }

    /**
     * 连接断开
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.err.println("断开连接: " + ctx.channel().remoteAddress().toString());
        super.channelInactive(ctx);
    }

    /**
     * 读取到的消息
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx , Object msg) throws Exception {
        System.out.println("服务器收到信息" + msg);
        //处理器处理
        rpcInvoker.handle(ctx.channel() , msg);
    }

    /**
     * 出现异常
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx , Throwable cause) throws Exception {
        System.err.println("出现异常: " + ctx.channel().remoteAddress().toString());
        super.exceptionCaught(ctx , cause);
    }
}
