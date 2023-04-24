package com.havad.smartcampusmanagementsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.havad.smartcampusmanagementsystem.mapper.ClassMapper;
import com.havad.smartcampusmanagementsystem.mapper.StudentMapper;
import com.havad.smartcampusmanagementsystem.pojo.Class;
import com.havad.smartcampusmanagementsystem.pojo.Student;
import com.havad.smartcampusmanagementsystem.service.ClassService;
import com.havad.smartcampusmanagementsystem.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: SmartCampusManagementSystem
 * @description: StudentService的实现类
 * @author: Havad
 * @create: 2023-04-25 07:00
 **/

@Service("StudentServiceImpl")
@Transactional
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
}
