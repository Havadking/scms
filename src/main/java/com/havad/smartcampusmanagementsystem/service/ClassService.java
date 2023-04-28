package com.havad.smartcampusmanagementsystem.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.havad.smartcampusmanagementsystem.pojo.Clazz;

public interface ClassService extends IService<Clazz> {
    IPage<Clazz> getClassByOpr(Page<Clazz> page, Clazz clazz);
}
