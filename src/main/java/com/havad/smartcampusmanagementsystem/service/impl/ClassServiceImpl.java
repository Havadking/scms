package com.havad.smartcampusmanagementsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.havad.smartcampusmanagementsystem.mapper.AdminMapper;
import com.havad.smartcampusmanagementsystem.mapper.ClassMapper;
import com.havad.smartcampusmanagementsystem.pojo.Admin;
import com.havad.smartcampusmanagementsystem.pojo.Class;
import com.havad.smartcampusmanagementsystem.service.AdminService;
import com.havad.smartcampusmanagementsystem.service.ClassService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: SmartCampusManagementSystem
 * @description: ClassService的实现类
 * @author: Havad
 * @create: 2023-04-25 07:00
 **/

@Service("ClassServiceImpl")
@Transactional
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Class> implements ClassService {
}
