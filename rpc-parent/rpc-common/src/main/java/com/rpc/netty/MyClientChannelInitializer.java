package com.rpc.netty;

import com.rpc.netty.serilaze.JsonSerilizer;
import com.rpc.netty.codec.RpcDecoder;
import com.rpc.netty.codec.RpcEncoder;
import com.rpc.netty.invoke.RpcResponseInvoker;
import com.rpc.support.RpcResponse;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @Description: 客户端处理器
 * @author: zhoum
 * @Date: 2019-11-14
 * @Time: 11:25
 */
public class MyClientChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();
        //添加编解码器
        pipeline.addLast(new RpcEncoder(new JsonSerilizer()));
        pipeline.addLast(new RpcDecoder(new JsonSerilizer() , RpcResponse.class));
        //添加消息处理器
        pipeline.addLast(new ChannelClientMessageHandler(new RpcResponseInvoker()));

    }


}
