package com.megalith;


import com.megalith.client.ProxyUtil;
import com.megalith.entity.TestEntity;
import com.megalith.test.TestService;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-27
 * @Time: 14:41
 */
public class Consumer {

    public static void main(String[] args) {

        ProxyUtil proxyUtil = new ProxyUtil("127.0.0.1",8080);
        TestService proxy = proxyUtil.getProxy(TestService.class);
        TestEntity test = proxy.getTest("张三" , "123436");
        System.out.println(test);

    }
}
