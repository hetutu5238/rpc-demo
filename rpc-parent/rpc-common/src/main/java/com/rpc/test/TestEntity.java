package com.rpc.test;

import java.io.Serializable;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-27
 * @Time: 14:38
 */
public class TestEntity implements Serializable {

    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "TestEntity{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
