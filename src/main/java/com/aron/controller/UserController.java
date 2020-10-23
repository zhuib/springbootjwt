package com.aron.controller;

import com.aron.entity.User;
import com.aron.service.UserService;
import com.aron.utils.JWTUtils;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: zhuib
 * Date: 2020/10/23 10:01
 * Describe:
 */

@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/login")
    public Map<String, Object> login(User user) {
        log.info("用户名：[{}]", user.getName());
        log.info("密码：[{}]", user.getPassword());
        Map<String, Object> map = new HashMap<>();

        try {
            User userDB = userService.login(user);
            Map<String, String> payload = new HashMap<>();
            payload.put("id",userDB.getId());
            payload.put("name",userDB.getName());
            // 生成JWT的令牌
            String token = JWTUtils.getToken(payload);
            map.put("state",true);
            map.put("msg", "认证成功");
            map.put("token", token);  // 响应token
        }catch (Exception e) {
            map.put("state", false);
            map.put("msg",e.getMessage());
        }
        return map;
    }

    @PostMapping("/user/test")
    public Map<String, Object> test(HttpServletRequest request) {
        Map<String,Object> map = new HashMap<>();
        String token = request.getHeader("token");
        DecodedJWT verify = JWTUtils.verifyAndGetToken(token);
        String id = verify.getClaim("id").asString();
        String name = verify.getClaim("name").asString();
        log.info("id : [{}]", id);
        log.info("name : [{}]", name);
        // 处理自己业务逻辑
        map.put("state",true);
        map.put("msg","请求成功！");
        return map;
    }


    @PostMapping("/user/api")
    public Map<String, Object> test(String token) {
        Map<String,Object> map = new HashMap<>();
        log.info("当前token为：[{}]", token);
        try {
            DecodedJWT verity = JWTUtils.verifyAndGetToken(token);
            map.put("state",true);
            map.put("msg","请求成功！");
            return map;
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
        map.put("state",false);
        return map;
    }

}
