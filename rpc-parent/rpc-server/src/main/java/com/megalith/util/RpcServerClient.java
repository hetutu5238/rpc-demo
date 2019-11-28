package com.megalith.util;

import com.megalith.client.Client;
import com.megalith.client.ProxyUtil;
import com.megalith.client.Server;
import com.megalith.entity.TestEntity;
import com.megalith.test.TestService;


/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-27
 * @Time: 14:44
 */
public class RpcServerClient {

    public static void main(String[] args) throws InterruptedException {
       Server.init(new String[]{"com.megalith"});
    }
}
