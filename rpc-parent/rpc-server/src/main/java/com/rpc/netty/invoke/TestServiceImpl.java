package com.rpc.netty.invoke;

import com.rpc.anno.RpcService;
import com.rpc.test.TestEntity;
import com.rpc.test.TestService;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-27
 * @Time: 14:40
 */
@RpcService(TestService.class)
public class TestServiceImpl implements TestService {
    @Override
    public TestEntity getTest(String username , String password) {
        TestEntity testEntity = new TestEntity();
        testEntity.setUsername(username);
        testEntity.setPassword(password);
        return testEntity;
    }

    @Override
    public TestEntity getTest1(String username , String password) {
        TestEntity testEntity = new TestEntity();
        testEntity.setUsername(username);
        testEntity.setPassword(password);
        return testEntity;
    }
}
