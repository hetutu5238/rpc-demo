package com.megalith.handle;

import com.megalith.repo.ChannelRespotiry;
import com.megalith.repo.ClientRespotiry;
import com.megalith.server.RpcServer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-14
 * @Time: 11:40
 */
public class ChannelClientMessageHandler extends ChannelInboundHandlerAdapter {

    /**
     * 有新连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String address = ctx.channel().remoteAddress().toString();
        ClientRespotiry.put(address,ctx.channel());
        System.out.println("有新连接");
        super.channelActive(ctx);
    }

    /**
     * 连接断开
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String address = ctx.channel().remoteAddress().toString();
        ClientRespotiry.remove(address);
        System.out.println("连接断开");
        super.channelInactive(ctx);
    }

    /**
     * 读取到的消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx , Object msg) throws Exception {
        RpcServer.handleMsg(ctx.channel(),msg);
    }
    /**
     * 出现异常
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx , Throwable cause) throws Exception {
        System.err.println("出现异常: "+ctx.channel().remoteAddress().toString());
        super.exceptionCaught(ctx , cause);
    }
}
