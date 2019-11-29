package com.megalith.netty;

import com.megalith.netty.serilaze.JsonSerilizer;
import com.megalith.netty.codec.RpcDecoder;
import com.megalith.netty.codec.RpcEncoder;
import com.megalith.netty.invoke.RpcRequestInvoker;
import com.megalith.support.RpcRequest;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;


/**
 * @Description: 服务端处理器
 * @author: zhoum
 * @Date: 2019-11-14
 * @Time: 11:25
 */
public class MyServerChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new RpcDecoder(new JsonSerilizer() , RpcRequest.class));
        pipeline.addLast(new RpcEncoder(new JsonSerilizer()));
        pipeline.addLast(new ChannelServerMessageHandler(new RpcRequestInvoker()));
    }


}
