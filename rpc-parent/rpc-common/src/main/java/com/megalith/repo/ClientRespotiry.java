package com.megalith.repo;

import io.netty.channel.Channel;

import javax.xml.ws.Response;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-14
 * @Time: 11:46
 */
public class ClientRespotiry {

    private static Map<String, Channel> repo = new ConcurrentHashMap<>();

    public static void put(String key,Channel channel){
        if ( key == null || channel == null ){
            System.err.println("channel or it's name can't be null ");
            return;
        }
        repo.put(key,channel);
    }

    public static Collection<Channel> getAll(){
        return repo.values();
    }

    public static void remove(String key){
        repo.remove(key);
    }
    public static Channel getChannel(String key){
        return repo.get(key);
    }

    public static Channel  getFirst(){
        if ( repo.isEmpty() ){
            throw new RuntimeException("no service can be find");
        }
        Collection<Channel> values = repo.values();
        for (Channel value : values) {
            return value;
        }
        return null;
    }
}
