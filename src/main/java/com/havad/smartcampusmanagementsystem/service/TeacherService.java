package com.havad.smartcampusmanagementsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.havad.smartcampusmanagementsystem.pojo.LoginForm;
import com.havad.smartcampusmanagementsystem.pojo.Teacher;

public interface TeacherService extends IService<Teacher> {
    Teacher login(LoginForm loginInfo);

    Teacher getTeacherInfoById(Long userId);
}
