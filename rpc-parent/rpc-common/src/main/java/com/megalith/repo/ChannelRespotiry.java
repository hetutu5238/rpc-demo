package com.megalith.repo;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-14
 * @Time: 11:46
 */
public class ChannelRespotiry {

    private static Map<String, Channel> repo = new ConcurrentHashMap<>();

    public static void put(String key,Channel channel){
        if ( key == null || channel == null ){
            System.err.println("channel or it's name can't be null ");
            return;
        }
        repo.put(key,channel);
    }

    public static void remove(String key){
        repo.remove(key);
    }
    public static Channel getChannel(String key){
        return repo.get(key);
    }
}
