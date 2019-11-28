package com.megalith.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-04-10
 * @Time: 11:40
 */
public class ChannelSendUtil {

    public static void sendMsg(Channel channel,Object obj){
        channel.writeAndFlush(obj);
    }

}
