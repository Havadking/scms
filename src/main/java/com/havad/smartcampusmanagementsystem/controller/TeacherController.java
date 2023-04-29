package com.havad.smartcampusmanagementsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.havad.smartcampusmanagementsystem.pojo.Teacher;
import com.havad.smartcampusmanagementsystem.service.TeacherService;
import com.havad.smartcampusmanagementsystem.util.MD5Utils;
import com.havad.smartcampusmanagementsystem.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: SmartCampusManagementSystem
 * @description: 老师Controller层
 * @author: Havad
 * @create: 2023-04-25 23:31
 **/

@Api(tags = "教师需求实现")
// 异步交互
@RestController
// 请求路径
@RequestMapping("/sms/teacherController")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;


    @ApiOperation("实现教师的分页条件模糊查询")
    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public ResultUtils getTeacher(
            @ApiParam("页码") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("页大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("查询条件") Teacher teacher
    ){
        Page<Teacher> page = new Page<>(pageNo, pageSize);
        IPage<Teacher> teacherPage = teacherService.getTeacherByOpr(page, teacher);

        return ResultUtils.success(teacherPage);
    }

    @ApiOperation("添加或修改教师的信息")
    @PostMapping("/saveOrUpdateTeacher")
    public ResultUtils saveOrUpdateTeacher(
            @ApiParam("查询的教师的信息的json对象")
            @RequestBody Teacher teacher
    ){
        // 如果是新增的教师信息的话，要对密码进行加密
        if (teacher.getId() == null || teacher.getId() == 0){
            teacher.setPassword(MD5Utils.md5Encrypt(teacher.getPassword()));
        }
        teacherService.saveOrUpdate(teacher);

        return ResultUtils.success();
    }

    @ApiOperation("根据id删除教师信息")
    @DeleteMapping("/deleteTeacher")
    public ResultUtils deleteTeacherById(
            @ApiParam("要删除的教师的id集合")
            @RequestBody List<Integer> list
    ){
        teacherService.removeByIds(list);

        return ResultUtils.success();
    }












}
