package com.aron.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * Author: zhuib
 * Date: 2020/10/23 9:16
 * Describe:
 */
public class JWTUtils {

    private static final String SIGN = "!Q@W#E$R";

    /**
     * 生成token header.payload.sign
     */
    public static String getToken(Map<String, String> map) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7); // 默认7天过期

        // 创建jwt builder
        JWTCreator.Builder builder = JWT.create();

        // payload
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });

        String token = builder.withExpiresAt(instance.getTime())
                .sign(Algorithm.HMAC256(SIGN));
        return token;
    }

    /**
     * 验证token的合法性
     */
//    public static void verify(String token) {
//        JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
//    }

    public static DecodedJWT verifyAndGetToken(String token) {
        return JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
    }

    /**
     * 获取token信息方法
     */
    public static DecodedJWT getTokenInfo(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        return verify;
    }
}
