package com.megalith.client;

import com.megalith.entity.RpcRequest;
import com.megalith.repo.ClientRespotiry;
import com.megalith.support.RpcFuture;
import io.netty.channel.Channel;


/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-27
 * @Time: 11:41
 */
public class RpcClient {


    public Object transfer(RpcRequest rpcRequest){
        //此处应该有一个统一的注册中心，这样可以根据服务名找到对应的服务进行调用 此处就用第一个了
        Long id = rpcRequest.getId();
        RpcFuture rpcFuture = new RpcFuture(id,rpcRequest);
        RpcFuture.putFuture(id,rpcFuture);
        Channel channel = ClientRespotiry.getFirst();
        channel.writeAndFlush(rpcRequest);
        return rpcFuture.getResponse();
    }
}
