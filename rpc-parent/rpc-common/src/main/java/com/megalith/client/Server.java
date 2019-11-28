package com.megalith.client;

import com.megalith.handle.MyServerChannelHandler;
import com.megalith.server.RpcServer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Description:  客户1
 * @author: zhoum
 * @Date: 2019-11-14
 * @Time: 10:41
 */
public class Server {


    private static ServerBootstrap server;

    public static void init(String[] pkgs) throws InterruptedException {
        System.err.println("开始初始化 端口:"+8080);
        RpcServer.initNetty(pkgs);
        if ( server == null ){
            server  = new ServerBootstrap();
        }
        server.group(parentGroup(),workerGroup()) //主线程与工作线程
                .channel(NioServerSocketChannel.class)//指定通道类型 此处是服务端  所以是server
                .handler(new LoggingHandler())
                .childHandler(new MyServerChannelHandler())
                .option(ChannelOption.SO_BACKLOG,100)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .bind(8080)
                .sync()
                .channel().closeFuture().sync();


    }

    private static EventLoopGroup parentGroup(){
        return new NioEventLoopGroup(1);
    }

    private static EventLoopGroup workerGroup(){
        return new NioEventLoopGroup(10);
    }

}
