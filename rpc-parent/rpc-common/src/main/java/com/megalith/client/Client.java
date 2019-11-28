package com.megalith.client;

import com.megalith.handle.MyClientChannelHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-27
 * @Time: 17:18
 */
public class Client {


    private static Bootstrap server;

    public static Channel init() throws InterruptedException {
        if ( server == null ){
            server  = new Bootstrap();
        }
        Channel channel = server.group(workerGroup())
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress("localhost" , 8080))
                .handler(new MyClientChannelHandler())
                .connect()
                .sync()
                .channel();
        return  channel;


    }
    private static EventLoopGroup workerGroup(){
        return new NioEventLoopGroup(10);
    }
}
