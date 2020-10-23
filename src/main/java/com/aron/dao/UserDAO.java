package com.aron.dao;

import com.aron.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * Author: zhuib
 * Date: 2020/10/23 9:54
 * Describe:
 */
@Mapper
public interface UserDAO {
    User login(User user);
}
