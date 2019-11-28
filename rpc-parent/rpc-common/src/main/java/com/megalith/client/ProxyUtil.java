package com.megalith.client;

import com.alibaba.fastjson.JSONObject;
import com.megalith.entity.RpcRequest;

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

    private String host;

    private int port;

    public ProxyUtil(String host , int port) {
        this.host = host;
        this.port = port;
    }

    public <T>T getProxy(Class<T> t){
        T o = (T)Proxy.newProxyInstance(t.getClassLoader() , new Class<?>[]{t} , this);
        return o;
    }


    @Override
    public Object invoke(Object proxy , Method method , Object[] args) throws Throwable {
        //这儿进行代理调用
        RpcRequest request = new RpcRequest();
        request.newId();
        request.setServiceName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParams(args);
        request.setParamsTypes(method.getParameterTypes());
        RpcClient client = new RpcClient();
        Object resp = client.transfer(request);
        if ( resp ==  null || resp == null){
            throw new RuntimeException("返回结果序列化错误");
        }
        if ( resp instanceof JSONObject ){
            Class<?> returnType = method.getReturnType();
            JSONObject jobj = (JSONObject)resp;
            Object o = jobj.toJavaObject(returnType);
            return o;
        }

        return resp;
    }
}
