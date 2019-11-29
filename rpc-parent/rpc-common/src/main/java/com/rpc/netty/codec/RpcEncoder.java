package com.rpc.netty.codec;

import com.rpc.netty.serilaze.Serilazier;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-28
 * @Time: 11:45
 */
public class RpcEncoder extends MessageToByteEncoder<Object> {

    private Serilazier serilazier;

    public RpcEncoder(Serilazier serilazier) {
        this.serilazier = serilazier;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx , Object msg , ByteBuf out) {
        byte[] serialize = serilazier.serialize(msg);
        out.writeInt(serialize.length);
        out.writeBytes(serialize);
    }
}
