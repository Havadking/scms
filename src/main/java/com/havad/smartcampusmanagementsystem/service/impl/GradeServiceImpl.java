package com.havad.smartcampusmanagementsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.havad.smartcampusmanagementsystem.mapper.GradeMapper;
import com.havad.smartcampusmanagementsystem.pojo.Grade;
import com.havad.smartcampusmanagementsystem.service.GradeService;
import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: SmartCampusManagementSystem
 * @description: GradeService的实现类
 * @author: Havad
 * @create: 2023-04-25 07:00
 **/


@Service("gradeServiceImpl")
@Transactional
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {
    @Override
    public IPage<Grade> getGradeByOpr(Page<Grade> page, String gradeName) {
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();

        // 判断gradeName是否为空
        if (!StringUtils.isNullOrEmpty(gradeName)) {
            // like模糊匹配
            queryWrapper.like("name",gradeName);
        }
        // 根据id进行升序排列
        queryWrapper.orderByAsc("id");

        // 返回查询的page
        return baseMapper.selectPage(page, queryWrapper);
    }
}
