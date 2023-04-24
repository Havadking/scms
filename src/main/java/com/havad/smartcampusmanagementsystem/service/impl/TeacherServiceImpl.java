package com.havad.smartcampusmanagementsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.havad.smartcampusmanagementsystem.mapper.StudentMapper;
import com.havad.smartcampusmanagementsystem.mapper.TeacherMapper;
import com.havad.smartcampusmanagementsystem.pojo.Student;
import com.havad.smartcampusmanagementsystem.pojo.Teacher;
import com.havad.smartcampusmanagementsystem.service.StudentService;
import com.havad.smartcampusmanagementsystem.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: SmartCampusManagementSystem
 * @description: TeacherService的实现类
 * @author: Havad
 * @create: 2023-04-25 07:00
 **/

@Service("TeacherServiceImpl")
@Transactional
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {
}
