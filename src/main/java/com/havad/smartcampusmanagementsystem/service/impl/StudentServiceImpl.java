package com.havad.smartcampusmanagementsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.havad.smartcampusmanagementsystem.mapper.StudentMapper;
import com.havad.smartcampusmanagementsystem.pojo.LoginForm;
import com.havad.smartcampusmanagementsystem.pojo.Student;
import com.havad.smartcampusmanagementsystem.service.StudentService;
import com.havad.smartcampusmanagementsystem.util.MD5Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: SmartCampusManagementSystem
 * @description: StudentService的实现类
 * @author: Havad
 * @create: 2023-04-25 07:00
 **/

@Service("studentServiceImpl")
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Override
    public Student login(LoginForm loginInfo) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", loginInfo.getUsername());
        String encrypt = MD5Utils.md5Encrypt(loginInfo.getPassword());
        queryWrapper.eq("password", encrypt);


        return baseMapper.selectOne(queryWrapper);
    }
}
