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
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @ApiOperation("删除班级的信息")
    @DeleteMapping("/deleteClazz")
    public ResultUtils deleteClass(@ApiParam("要删除的班级id集合") @RequestBody List<Integer> list){

        // iservice接口中实现的函数
        classService.removeByIds(list);
        return ResultUtils.success();
    }


    @ApiOperation("添加或修改班级的信息")
    @PostMapping("/saveOrUpdateClazz")
    public ResultUtils addOrUpdateClazz(
           @ApiParam("添加或修改的班级信息的json格式")
           @RequestBody Clazz clazz
    ){
        // iService中已经实现了
        classService.saveOrUpdate(clazz);

        return ResultUtils.success();
    }




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
