package com.havad.smartcampusmanagementsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.havad.smartcampusmanagementsystem.pojo.Student;
import com.havad.smartcampusmanagementsystem.service.StudentService;
import com.havad.smartcampusmanagementsystem.util.MD5Utils;
import com.havad.smartcampusmanagementsystem.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: SmartCampusManagementSystem
 * @description: 学生Controller层
 * @author: Havad
 * @create: 2023-04-25 23:31
 **/

@Api(tags = "学生需求实现")
// 异步交互
@RestController
// 请求路径
@RequestMapping("/sms/studentController")
public class StudentController {

    @Autowired
    private StudentService studentService;


    @PostMapping("/addOrUpdateStudent")
    public ResultUtils addOrUpdateStudent(@ApiParam("要修改或新增的学生信息的json格式") @RequestBody Student student){
        Integer id = student.getId();
        if (id == null || id.equals(0)){
            student.setPassword(MD5Utils.md5Encrypt(student.getPassword()));
        }
        studentService.saveOrUpdate(student);
        return ResultUtils.success();
    }


    @ApiOperation("学生信息分页模糊查询")
    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public ResultUtils getStudentByOpr(
            @ApiParam("页码") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("页大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("查询条件")Student student
    ){
        Page<Student> page = new Page<>(pageNo, pageSize);
        IPage<Student> studentPage = studentService.getStudentByOpr(page, student);

        return ResultUtils.success(studentPage);
    }


}
