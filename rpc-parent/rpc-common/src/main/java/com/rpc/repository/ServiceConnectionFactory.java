package com.rpc.repository;

import com.rpc.netty.MyClientChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-27
 * @Time: 17:18
 */
public class ServiceConnectionFactory {


    public static Channel createConnection(String host , int port) throws InterruptedException {
        String key = host + ":" + port;
        System.out.println("创建新连接：" + key);
        if ( ClientRepository.getChannel(key) != null ) {
            ClientRepository.remove(key);
        }
        Bootstrap server = new Bootstrap();
        Channel c = server.group(workerGroup())
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress(host , port))
                .handler(new MyClientChannelInitializer())
                .connect()
                .sync()
                .channel();
        ClientRepository.put(key , c);
        return c;


    }

    private static EventLoopGroup workerGroup() {
        return new NioEventLoopGroup(10, Executors.newFixedThreadPool(10));
    }
}
