package com.havad.smartcampusmanagementsystem.pojo;

import lombok.Data;

/**
 * @program: SmartCampusManagementSystem
 * @description: 负责登录的类
 * @author: Havad
 * @create: 2023-04-25 06:49
 **/

@Data
public class LoginForm {

    private String username;
    private String password;
    private String verifiCode;
    private Integer userType;

}
