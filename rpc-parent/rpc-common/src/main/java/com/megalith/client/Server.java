package com.megalith.client;

import com.megalith.config.RegisterConfig;
import com.megalith.netty.MyServerChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Description: 客户1
 * @author: zhoum
 * @Date: 2019-11-14
 * @Time: 10:41
 */
public class Server {

    private static volatile boolean init = true;

    private static ServerBootstrap server;

    private static volatile boolean start = false;

    public static void init() throws InterruptedException {
        if ( start ) {
            return;
        }
        System.err.println("开始初始化 端口:");
        //注册服务
        RegisterConfig.init(true);
        if ( server == null ) {
            server = new ServerBootstrap();
            start = true;
        }
        Channel channel = server.group(parentGroup() , workerGroup())
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler())
                .childHandler(new MyServerChannelInitializer())
                .option(ChannelOption.SO_BACKLOG , 100)
                .childOption(ChannelOption.SO_KEEPALIVE , true)
                .bind(RegisterConfig.getPort())
                .sync()
                .channel();

        channel.closeFuture().sync();


    }

    private static EventLoopGroup parentGroup() {
        return new NioEventLoopGroup(1);
    }

    private static EventLoopGroup workerGroup() {
        return new NioEventLoopGroup(10);
    }

}
