package com.megalith.netty.invoke;

import com.megalith.netty.RpcInvoker;
import com.megalith.support.RpcRequest;
import com.megalith.support.RpcResponse;


import com.megalith.repository.ServiceRepository;
import io.netty.channel.Channel;

import java.lang.reflect.Method;

/**
 * RPC-SERVER
 *
 * @author: zhoum
 * @Date: 2019-11-26
 * @Time: 11:50
 */
public class RpcRequestInvoker implements RpcInvoker {

    @Override
    public void handle(Channel channel , Object object) {
        RpcResponse res = new RpcResponse();
        try {
            RpcRequest request;
            if ( !(object instanceof RpcRequest) ) {
                res.setError(new Exception("params must be instance of com.megalith.support.RpcRequest"));
                channel.writeAndFlush(res);
                return;
            }
            request = (RpcRequest) object;
            res.setId(request.getId());
            //如果是正常参数
            Object service = ServiceRepository.getService(request.getServiceName());
            if ( service == null ) {
                res.setError(new Exception("can't find service for " + request.getServiceName()));
                channel.writeAndFlush(res);
                return;
            }
            Method method = service.getClass().getMethod(request.getMethodName() , request.getParamsTypes());
            Object invoke = method.invoke(service , request.getParams());
            res.setResponse(invoke);
            channel.writeAndFlush(res);
        } catch (Exception e) {
            res.setError(e);
            channel.writeAndFlush(res);
        }
    }

}
