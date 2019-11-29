package com.megalith.util;

/**
 * @Description:
 * @author: zhoum
 * @Date: 2019-11-29
 * @Time: 13:54
 */
public class Assert {

    private Assert(){};

    public static void notNull(Object obj, String message) {
        if (obj == null) {
            throw new RuntimeException(message);
        }
    }

    public static void on(boolean flag, String message) {
        if (flag) {
            throw new RuntimeException(message);
        }
    }
}
