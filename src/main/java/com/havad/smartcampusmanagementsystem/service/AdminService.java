package com.havad.smartcampusmanagementsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.havad.smartcampusmanagementsystem.pojo.Admin;
import com.havad.smartcampusmanagementsystem.pojo.LoginForm;

public interface AdminService extends IService<Admin> {
    Admin login(LoginForm loginInfo);

    Admin getAdminInfoById(Long userId);

    IPage<Admin> getAdminByOpr(Page<Admin> page, String adminName);
}
