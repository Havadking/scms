package com.havad.smartcampusmanagementsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.havad.smartcampusmanagementsystem.pojo.Admin;
import com.havad.smartcampusmanagementsystem.pojo.LoginIn;

public interface AdminService extends IService<Admin> {
    Admin login(LoginIn loginInfo);
}
