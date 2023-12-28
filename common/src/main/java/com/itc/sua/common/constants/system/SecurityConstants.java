package com.itc.sua.common.constants.system;

/**
 * @ClassName SecurityConstants
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/15
 */
public interface SecurityConstants {

    interface AuthHeader {
        String TOKEN = "Authorization";
        String USER_ID = "user-id";
    }

    interface RedisKey {
        String IMAGE_CODE_KEY = "system:code:imageCode:";
        String SMS_CODE_KEY = "system:smsCode:";
        String DAY_COUNT_KEY = "system:smsCode:count:";
        String PERIOD_COUNT_KEY = "system:smsCode:period:";
        String LOGIN_COUNT_KEY = "system:smsCode:tryLogin:";

        String AUTH_USER = "system:auth:user:";
        String AUTH_MENU = "system:auth:menu:";
    }

    interface Limit {
        Integer SMS_CODE_DAY_LIMIT = 20;
        Integer SMS_CODE_VALID_PERIOD_MINUTE = 5;
        Integer SMS_CODE_PERIOD_LIMIT = 1;
        Integer SMS_CODE_LIMIT_TRY_LOGIN_TIMES = 3;
        Long TOKEN_RENEWAL_MILL = 1800000L;
    }

    interface Keyword {
        String JWT_KEY = "com.itc.sua.system.utils.JwtUtil.sussenn";
    }
}
