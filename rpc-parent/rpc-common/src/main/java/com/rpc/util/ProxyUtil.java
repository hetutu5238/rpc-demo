package com.rpc.util;

import com.alibaba.fastjson.JSONObject;
import com.rpc.client.RpcClient;
import com.rpc.support.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-27
 * @Time: 14:09
 */
public class ProxyUtil implements InvocationHandler {

    public <T> T getProxy(Class<T> t) {
        T o = (T) Proxy.newProxyInstance(t.getClassLoader() , new Class<?>[]{t} , this);
        return o;
    }


    @Override
    public Object invoke(Object proxy , Method method , Object[] args) throws Throwable {
        //创建请求
        RpcRequest request = new RpcRequest();
        //每次请求构造新的id
        request.newId();
        request.setServiceName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParams(args);
        request.setParamsTypes(method.getParameterTypes());
        Object resp = RpcClient.transfer(request);
        Assert.on(resp == null || resp == null,"返回结果序列化错误");
        if ( resp instanceof JSONObject ) {
            Class<?> returnType = method.getReturnType();
            JSONObject jobj = (JSONObject) resp;
            Object o = jobj.toJavaObject(returnType);
            return o;
        }

        return resp;
    }
}
