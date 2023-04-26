package com.havad.smartcampusmanagementsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.havad.smartcampusmanagementsystem.pojo.Admin;
import com.havad.smartcampusmanagementsystem.pojo.LoginIn;
import com.havad.smartcampusmanagementsystem.pojo.Teacher;

public interface TeacherService extends IService<Teacher> {
    Teacher login(LoginIn loginInfo);
}
