package com.megalith;


import com.megalith.client.Client;
import com.megalith.client.ProxyUtil;
import com.megalith.entity.TestEntity;
import com.megalith.test.TestService;
import io.netty.channel.Channel;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-27
 * @Time: 14:41
 */
public class Consumer {

    public static void main(String[] args) throws InterruptedException {
        Channel init = Client.init();
        System.out.println("初始化成功");
        ProxyUtil proxyUtil = new ProxyUtil("localhost" , 8080);
        TestService proxy = proxyUtil.getProxy(TestService.class);
        TestEntity test = proxy.getTest("你好" , "helloword");
        System.out.println("收到信息：" + test);
    }
}
