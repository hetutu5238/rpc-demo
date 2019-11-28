package com.megalith.regist;

import java.io.InputStream;
import java.util.Properties;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-28
 * @Time: 16:24
 */
public class RegisterConfig {

    private Properties register;

    public void init(String host,int port){
        Properties properties = new Properties();
        try(InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("register.properties");) {
            properties.load(resourceAsStream);
            register = properties;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 注册到注册中心
     */
    private void regis(String host,int port){

    }
}
