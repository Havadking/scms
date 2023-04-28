package com.havad.smartcampusmanagementsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.havad.smartcampusmanagementsystem.pojo.Clazz;
import com.havad.smartcampusmanagementsystem.service.ClassService;
import com.havad.smartcampusmanagementsystem.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: SmartCampusManagementSystem
 * @description: 班级Controller层
 * @author: Havad
 * @create: 2023-04-25 23:31
 **/

@Api(tags = "班级需求实现")
// 异步交互
@RestController
// 请求路径
@RequestMapping("/sms/clazzController")
public class ClassController {

    @Autowired
    private ClassService classService;


    @ApiOperation("班级分页条件模糊查询")
    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public ResultUtils getClazzByOpr(
            @ApiParam("页号") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("页大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("模糊查询的条件") Clazz clazz
    ){
        Page<Clazz> page = new Page<>(pageNo, pageSize);
        IPage<Clazz> iPage = classService.getClassByOpr(page, clazz);

        return ResultUtils.success(iPage);
    }
}
