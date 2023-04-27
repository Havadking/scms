package com.havad.smartcampusmanagementsystem.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.havad.smartcampusmanagementsystem.pojo.LoginForm;
import com.havad.smartcampusmanagementsystem.pojo.Student;

public interface StudentService extends IService<Student> {
    Student login(LoginForm loginInfo);
}
