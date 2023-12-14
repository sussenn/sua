package com.itc.sua.common.utils;

/**
 * @ClassName UserContext
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/13
 */
public class UserContext {
    private static final ThreadLocal<String> TL = new ThreadLocal<>();

    public static void setUser(String userId) {
        TL.set(userId);
    }

    public static String getUser() {
        return TL.get();
    }

    public static void removeUser() {
        TL.remove();;
    }

}
