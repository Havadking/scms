package com.havad.smartcampusmanagementsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.havad.smartcampusmanagementsystem.mapper.AdminMapper;
import com.havad.smartcampusmanagementsystem.pojo.Admin;
import com.havad.smartcampusmanagementsystem.pojo.LoginIn;
import com.havad.smartcampusmanagementsystem.service.AdminService;
import com.havad.smartcampusmanagementsystem.util.MD5Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: SmartCampusManagementSystem
 * @description: AdminService的实现类
 * @author: Havad
 * @create: 2023-04-25 07:00
 **/

@Service("adminServiceImpl")
@Transactional
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    @Override
    public Admin login(LoginIn loginInfo) {
        // 查询条件
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", loginInfo.getUserName());

        // 转换密码为密文
        String encrypt = MD5Utils.md5Encrypt(loginInfo.getPassword());
        queryWrapper.eq("password", encrypt);


        return baseMapper.selectOne(queryWrapper);
    }
}
