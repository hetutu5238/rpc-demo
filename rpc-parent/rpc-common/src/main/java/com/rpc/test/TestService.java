package com.rpc.test;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-27
 * @Time: 14:38
 */
public interface TestService {

    TestEntity getTest(String username , String password);

    TestEntity getTest1(String username , String password);
}
