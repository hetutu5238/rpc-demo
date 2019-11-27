package com.megalith.util;

import com.megalith.server.RpcServer;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-27
 * @Time: 14:44
 */
public class Server {

    public static void main(String[] args) {

        RpcServer rpcServer = new RpcServer();
        rpcServer.start(8080,new String[]{"com.megalith"});
    }
}
