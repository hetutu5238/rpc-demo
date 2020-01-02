package com.rpc.netty;

import com.rpc.netty.serilaze.HessianSerlizer;
import com.rpc.netty.serilaze.JsonSerilizer;
import com.rpc.netty.codec.RpcDecoder;
import com.rpc.netty.codec.RpcEncoder;
import com.rpc.netty.invoke.RpcRequestInvoker;
import com.rpc.support.RpcRequest;
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
        //添加编解码器
        pipeline.addLast(new RpcDecoder(new JsonSerilizer() , RpcRequest.class));
        pipeline.addLast(new RpcEncoder(new JsonSerilizer()));
        //添加消息处理器
        pipeline.addLast(new ChannelServerMessageHandler(new RpcRequestInvoker()));
    }


}
