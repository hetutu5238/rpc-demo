package com.megalith.config;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-28
 * @Time: 11:21
 */
public interface Serilazier {
    byte[] serialize(Object msg);
    <T> T deserialize(byte[] bytes, Class<T> clz);
}
