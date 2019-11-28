package com.megalith.handle;

import com.megalith.repo.ChannelRespotiry;
import com.megalith.server.RpcServer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Arrays;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-14
 * @Time: 11:40
 */
public class ChannelMessageHandler extends ChannelInboundHandlerAdapter {

    /**
     * 有新连接
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String address = ctx.channel().remoteAddress().toString();
        System.out.println("收到信息: "+address);
        ChannelRespotiry.put(address,ctx.channel());
        super.channelActive(ctx);
    }

    /**
     * 连接断开
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //TCP传输时获取ip与端口
//        InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
//        String ip = insocket.getAddress().getHostAddress();
//        int port = insocket.getPort();


        String address = ctx.channel().remoteAddress().toString();
        ChannelRespotiry.remove(address);
        System.err.println("断开连接: "+address);
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
        System.out.println("收到信息"+ msg);
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
