package com.havad.smartcampusmanagementsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.havad.smartcampusmanagementsystem.pojo.Grade;
import com.havad.smartcampusmanagementsystem.service.GradeService;
import com.havad.smartcampusmanagementsystem.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: SmartCampusManagementSystem
 * @description: 年纪Controller层
 * @author: Havad
 * @create: 2023-04-25 23:31
 **/


@Api(tags = "年级需求实现")
// 异步交互
@RestController
// 请求路径
@RequestMapping("/sms/gradeController")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @ApiOperation("查询全部的年级的信息")
    @GetMapping("/getGrades")
    public ResultUtils getGrades(){
        List<Grade> grades = gradeService.getAllGrades();

        return ResultUtils.success(grades);
    }


    @ApiOperation("删除Grade数据")
    @DeleteMapping("/deleteGrade")
    public ResultUtils deleteGrade(@ApiParam("要删除的年级的id的json集合") @RequestBody List<Integer> list){
        gradeService.removeByIds(list);
        return ResultUtils.success();
    }


    @ApiOperation("添加或修改年级数据。有id则修改，没有id就增加")
    @PostMapping("saveOrUpdateGrade")
    public ResultUtils addOrUpdateGrade(@ApiParam("要修改的Json格式的Grade对象") @RequestBody Grade grade){
        // 接受参数
        // 使用@RequestBody 注解将Json转换为grade

        // 调用service层完成添加或修改
        // iservice中已经实现了
        boolean b = gradeService.saveOrUpdate(grade);

        return ResultUtils.success();
    }





    @ApiOperation("根据名称进行模糊的分页查询")
    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public ResultUtils getGrades(
            @ApiParam("当前的页号") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("每页的大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("模糊查询的条件") String gradeName
    ){
        // 分页带条件查询
        // 分页信息
        Page<Grade> page = new Page<>(pageNo, pageSize);

        // 通过Service层查询
        IPage<Grade> pageResult = gradeService.getGradeByOpr(page, gradeName);

        return ResultUtils.success(pageResult);
    }

}
