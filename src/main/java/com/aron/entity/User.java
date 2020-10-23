package com.aron.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Author: zhuib
 * Date: 2020/10/23 9:49
 * Describe:
 */
@Data
@Accessors(chain = true)
public class User {

    private String id;
    private String name;
    private String password;
}
