package com.megalith.client;

import com.megalith.entity.RpcRequest;
import com.megalith.entity.RpcResponse;

import java.io.*;
import java.net.Socket;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-27
 * @Time: 11:41
 */
public class RpcClient {


    public Object transfer(RpcRequest rpcRequest,String host,int port){
        InputStream in = null;
        ObjectInputStream objIn  = null;
        try(Socket socket = new Socket(host,port);
            OutputStream out = socket.getOutputStream();
            ObjectOutputStream objOut = new ObjectOutputStream(out)) {
            objOut.writeObject(rpcRequest);
            objOut.flush();
            //获取返回结果
            in = socket.getInputStream();
            objIn = new ObjectInputStream(in);
            Object o = objIn.readObject();
            if ( o == null || !(o instanceof RpcResponse) ){
                throw new RuntimeException("获取返回值异常");
            }
            RpcResponse response = (RpcResponse)o;
            if ( response.getError() != null ){
                throw new RuntimeException(response.getError());
            }
            return response.getResponse();

        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            try {
                if ( in != null ){
                    in.close();
                }
                if ( objIn!= null ){
                    objIn.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }
}
