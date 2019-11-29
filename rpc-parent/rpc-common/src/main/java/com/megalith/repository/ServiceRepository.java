package com.megalith.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-14
 * @Time: 11:46
 */
public class ServiceRepository {

    private static final Map<String, Object> repo = new HashMap<>();

    public static void put(String serviceName , Object service) {
        repo.put(serviceName , service);
    }

    public static void remove(String key) {
        repo.remove(key);
    }

    public static Object getService(String serviceName) {
        return repo.get(serviceName);
    }

    public static Set<String> getAllServiceName() {
        return repo.keySet();
    }
}
