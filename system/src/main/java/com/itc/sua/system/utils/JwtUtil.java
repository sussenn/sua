package com.itc.sua.system.utils;

import com.itc.sua.common.constants.system.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

/**
 * @ClassName JwtUtil
 * @Author sussenn
 * @Version 1.0.0
 * @Date 2023/12/20
 */
public class JwtUtil {

    private static final SecretKey KEY = Keys.hmacShaKeyFor(SecurityConstants.Keyword.JWT_KEY.getBytes());

    /**
     * 生成token
     */
    public static String createToken(String userId, String userName) {
        return Jwts.builder()
                .subject(userId)
                .claim("username", userName)
                .signWith(KEY, Jwts.SIG.HS256)
                .compact();
    }

    /**
     * 解析token
     */
    public static Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
