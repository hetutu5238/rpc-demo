package com.rpc.netty.codec;

import com.rpc.netty.serilaze.Serilazier;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-28
 * @Time: 11:47
 */
public class RpcDecoder extends ByteToMessageDecoder {

    private Serilazier serilazier;

    private Class<?> c;

    public RpcDecoder(Serilazier serilazier , Class<?> c) {
        this.serilazier = serilazier;
        this.c = c;
    }


    @Override
    protected void decode(ChannelHandlerContext ctx , ByteBuf in , List<Object> out) throws Exception {
        if ( in.readableBytes() < 4 ) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        if ( dataLength < 0 ) {
            ctx.close();
        }
        if ( in.readableBytes() < dataLength ) {
            in.resetReaderIndex();
            return;
        }
        byte[] bytes = new byte[ dataLength ];
        in.readBytes(bytes);
        Object obj = serilazier.deserialize(bytes , c);
        out.add(obj);
    }
}
