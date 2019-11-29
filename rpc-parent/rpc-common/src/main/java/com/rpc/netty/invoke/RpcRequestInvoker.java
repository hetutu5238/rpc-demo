package com.rpc.netty.invoke;

import com.rpc.netty.RpcInvoker;
import com.rpc.support.RpcRequest;
import com.rpc.support.RpcResponse;


import com.rpc.repository.ServiceRepository;
import io.netty.channel.Channel;

import java.lang.reflect.Method;

/**
 * RpcRequest请求处理器
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
                res.setError(new Exception("params must be instance of com.rpc.support.RpcRequest"));
                channel.writeAndFlush(res);
                return;
            }
            request = (RpcRequest) object;
            //将请求的id直接赋给响应实体
            res.setId(request.getId());
            //找到该服务类的处理类
            Object service = ServiceRepository.getService(request.getServiceName());
            if ( service == null ) {
                res.setError(new Exception("can't find service for " + request.getServiceName()));
                channel.writeAndFlush(res);
                return;
            }
            //执行方法
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
