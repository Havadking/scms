package com.havad.smartcampusmanagementsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.havad.smartcampusmanagementsystem.mapper.StudentMapper;
import com.havad.smartcampusmanagementsystem.pojo.LoginForm;
import com.havad.smartcampusmanagementsystem.pojo.Student;
import com.havad.smartcampusmanagementsystem.service.StudentService;
import com.havad.smartcampusmanagementsystem.util.MD5Utils;
import com.mysql.cj.util.StringUtils;
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

    @Override
    public Student getStudentInfoById(Long userId) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", userId);

        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<Student> getStudentByOpr(Page<Student> page, Student student) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        String name = student.getName();
        if (!StringUtils.isNullOrEmpty(name)) {
            queryWrapper.like("name", name);
        }
        String clazzName = student.getClazzName();
        if (!StringUtils.isNullOrEmpty(clazzName)){
            queryWrapper.like("clazz_name", clazzName);
        }

        queryWrapper.orderByAsc("id");

        return baseMapper.selectPage(page, queryWrapper);

    }
}
