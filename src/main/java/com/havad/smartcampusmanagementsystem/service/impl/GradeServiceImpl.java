package com.havad.smartcampusmanagementsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.havad.smartcampusmanagementsystem.mapper.ClassMapper;
import com.havad.smartcampusmanagementsystem.mapper.GradeMapper;
import com.havad.smartcampusmanagementsystem.pojo.Class;
import com.havad.smartcampusmanagementsystem.pojo.Grade;
import com.havad.smartcampusmanagementsystem.service.ClassService;
import com.havad.smartcampusmanagementsystem.service.GradeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: SmartCampusManagementSystem
 * @description: GradeService的实现类
 * @author: Havad
 * @create: 2023-04-25 07:00
 **/

@Service("GradeServiceImpl")
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {
}
