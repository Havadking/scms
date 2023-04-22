package com.havad.smartcampusmanagementsystem.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: SmartCampusManagementSystem
 * @description: Mybatis配置类
 * @author: Havad
 * @create: 2023-04-22 21:23
 **/
@Configuration
@MapperScan("com.havad.smartcampusmanagementsystem.mapper")
public class MybatisConfig {
    // 分页插件配置
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置最大单页限制数量（默认500)
        paginationInterceptor.setLimit(200);
        return paginationInterceptor;
    }
}
