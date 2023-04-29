package com.havad.smartcampusmanagementsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.havad.smartcampusmanagementsystem.mapper.ClassMapper;
import com.havad.smartcampusmanagementsystem.pojo.Clazz;
import com.havad.smartcampusmanagementsystem.service.ClassService;
import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: SmartCampusManagementSystem
 * @description: ClassService的实现类
 * @author: Havad
 * @create: 2023-04-25 07:00
 **/

@Service("classServiceImpl")
@Transactional
public class ClassServiceImpl extends ServiceImpl<ClassMapper, Clazz> implements ClassService {
    @Override
    public IPage<Clazz> getClassByOpr(Page<Clazz> page, Clazz clazz) {
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<>();

        String gradeName = clazz.getGradeName();
        if (!StringUtils.isNullOrEmpty(gradeName)){
            // 年级不为空
            queryWrapper.like("grade_name", gradeName);
        }

        String name = clazz.getName();
        if (!StringUtils.isNullOrEmpty(name)){
            // 年级不为空
            queryWrapper.like("name", name);
        }
        queryWrapper.orderByAsc("id");

        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public List<Clazz> getAllClazzs() {
        return baseMapper.selectList(null);
    }
}
