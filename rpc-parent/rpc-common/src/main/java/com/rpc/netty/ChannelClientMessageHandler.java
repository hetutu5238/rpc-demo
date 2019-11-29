package com.rpc.netty;

import com.rpc.repository.ClientRepository;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;


/**
 * netty请求处理适配器
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-14
 * @Time: 11:40
 */
public class ChannelClientMessageHandler extends AbstractHandleAdapter {

    public ChannelClientMessageHandler(RpcInvoker rpcInvoker) {
        super(rpcInvoker);
    }
    /**
     * 有新连接
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String address = ctx.channel().remoteAddress().toString();
        System.out.println("有新连接:"+address);
        super.channelActive(ctx);
    }

    /**
     * 连接断开
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress ipSocket = (InetSocketAddress)ctx.channel().remoteAddress();
        //移除连接
        ClientRepository.remove(ipSocket.getAddress().getHostAddress()+":"+ipSocket.getPort());
        System.out.println("连接断开");
        super.channelInactive(ctx);
    }

    /**
     * 读取到的消息
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx , Object msg)  {
        //处理器处理
        rpcInvoker.handle(ctx.channel() , msg);
    }

    /**
     * 出现异常
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx , Throwable cause) throws Exception {
        System.err.println("出现异常: " + ctx.channel().remoteAddress().toString());
        InetSocketAddress ipSocket = (InetSocketAddress)ctx.channel().remoteAddress();
        //移除连接
        ClientRepository.remove(ipSocket.getAddress().getHostAddress()+":"+ipSocket.getPort());
        super.exceptionCaught(ctx , cause);
    }
}
