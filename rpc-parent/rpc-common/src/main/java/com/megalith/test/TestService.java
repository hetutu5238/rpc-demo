package com.megalith.test;

import com.megalith.entity.TestEntity;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-27
 * @Time: 14:38
 */
public interface TestService {

    TestEntity getTest(String username,String password);
}
