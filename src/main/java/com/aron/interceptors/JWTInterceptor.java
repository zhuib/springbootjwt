package com.aron.interceptors;

import com.aron.utils.JWTUtils;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: zhuib
 * Date: 2020/10/23 13:07
 * Describe:
 */
public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String,Object> map = new HashMap<>();
        // 获取请求头中令牌
        String token = request.getHeader("token");
        try {
            JWTUtils.verifyAndGetToken(token);  // 验证令牌
//            map.put("state",true);
//            map.put("msg","请求成功！");
            return true;  // 放行请求
        }catch (SignatureVerificationException e) {
            e.printStackTrace();
            map.put("msg","无效签名");
        }catch (TokenExpiredException e) {
            e.printStackTrace();
            map.put("msg","token过期");
        }catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            map.put("msg","token算法不一致！");
        }catch (Exception e) {
            e.printStackTrace();
            map.put("msg","token无效");
        }
        map.put("state",false);  // 设置状态
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
