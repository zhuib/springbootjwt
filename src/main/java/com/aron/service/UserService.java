package com.aron.service;

import com.aron.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: zhuib
 * Date: 2020/10/23 9:57
 * Describe:
 */
@Service
public interface UserService {

    User login(User user); // 登录接口
}
