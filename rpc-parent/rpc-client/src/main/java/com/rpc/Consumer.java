package com.rpc;


import com.rpc.util.ProxyUtil;
import com.rpc.test.TestEntity;
import com.rpc.test.TestService;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-27
 * @Time: 14:41
 */
public class Consumer {

    public static void main(String[] args) {
        ProxyUtil proxyUtil = new ProxyUtil();
        TestService proxy = proxyUtil.getProxy(TestService.class);
        int i = 1;
        while ( i < 500 ) {
            TestEntity test = proxy.getTest("你好" + i++ , "helloword" + i);
            TestEntity test1 = proxy.getTest1("你好" + i++ , "helloword" + i);
            System.out.println("收到信息：" + test);
            System.out.println("收到信息：" + test1);
        }
    }
}
