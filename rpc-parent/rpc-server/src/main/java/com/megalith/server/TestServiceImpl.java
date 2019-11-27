package com.megalith.server;

import com.megalith.anno.RpcService;
import com.megalith.entity.TestEntity;
import com.megalith.test.TestService;

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
}
