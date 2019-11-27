package com.megalith.task;

import com.megalith.entity.RpcRequest;
import com.megalith.entity.RpcResponse;

import javax.xml.ws.Response;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-27
 * @Time: 10:42
 */
public class RpcTask implements Runnable {

    private Socket socket;

    private Map<String,Object> services;

    public RpcTask(Socket socket , Map<String, Object> services) {
        this.socket = socket;
        this.services = services;
    }

    @Override
    public void run() {
        ObjectOutputStream obs = null;
        try(InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();
            ObjectInputStream objIn = new ObjectInputStream(inputStream);
            ObjectOutputStream objOut = new ObjectOutputStream(outputStream);) {
            obs = objOut;
            Object o = objIn.readObject();
            RpcResponse res = new RpcResponse();
            //如果是非法参数
            if (!( o instanceof RpcRequest) ){
                res.setError(new Exception("params must be instance of com.megalith.entity.RpcRequest"));
                objOut.writeObject(res);
                objOut.flush();
                return;
            }
            //如果是正常参数
            RpcRequest request = (RpcRequest)o;
            Object service = services.get(request.getServiceName());
            if ( service == null ){
                res.setError(new Exception("can't find service for "+request.getServiceName()));
                objOut.writeObject(res);
                objOut.flush();
                return;
            }
            Class<?> c = service.getClass();
            Method method = c.getMethod(request.getMethodName() , request.getParamsTypes());
            Object invoke = method.invoke(service , request.getParams());
            res.setResponse(invoke);
            objOut.writeObject(res);
            return;
        } catch (Exception e) {
            e.printStackTrace();
            if ( obs != null){
                RpcResponse res = new RpcResponse();
                res.setError(e);
                try {
                    obs.writeObject(res);
                    obs.flush();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                return;
            }

        }finally {
            if ( socket != null ){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}
