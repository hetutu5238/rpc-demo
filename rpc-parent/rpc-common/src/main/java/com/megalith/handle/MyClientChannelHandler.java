package com.megalith.handle;

import com.megalith.config.JsonSerilizer;
import com.megalith.entity.RpcResponse;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @Description: 子线程处理器
 * @author: zhoum
 * @Date: 2019-11-14
 * @Time: 11:25
 */
public class MyClientChannelHandler extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch)  {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new RpcEncoder(new JsonSerilizer()));
        pipeline.addLast(new RpcDecoder(new JsonSerilizer(), RpcResponse.class));
        pipeline.addLast(new ChannelClientMessageHandler());
    }


}
