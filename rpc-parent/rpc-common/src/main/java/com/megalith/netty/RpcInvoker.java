package com.megalith.netty;

import io.netty.channel.Channel;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-28
 * @Time: 17:46
 */
public interface RpcInvoker {

    void handle(Channel channel , Object object);
}
