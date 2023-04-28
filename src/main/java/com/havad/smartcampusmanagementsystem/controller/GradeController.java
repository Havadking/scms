package com.havad.smartcampusmanagementsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.havad.smartcampusmanagementsystem.pojo.Grade;
import com.havad.smartcampusmanagementsystem.service.GradeService;
import com.havad.smartcampusmanagementsystem.util.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: SmartCampusManagementSystem
 * @description: 年纪Controller层
 * @author: Havad
 * @create: 2023-04-25 23:31
 **/
// 异步交互
@RestController
// 请求路径
@RequestMapping("/sms/gradeController")
public class GradeController {

    @Autowired
    private GradeService gradeService;


    @PostMapping("saveOrUpdateGrade")
    public ResultUtils addOrUpdateGrade(@RequestBody Grade grade){
        // 接受参数
        // 使用@RequestBody 注解将Json转换为grade

        // 调用service层完成添加或修改
        // iservice中已经实现了
        boolean b = gradeService.saveOrUpdate(grade);

        return ResultUtils.success();
    }






    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public ResultUtils getGrades(
            @PathVariable("pageNo") Integer pageNo,
            @PathVariable("pageSize") Integer pageSize,
            String gradeName
    ){
        // 分页带条件查询
        // 分页信息
        Page<Grade> page = new Page<>(pageNo, pageSize);

        // 通过Service层查询
        IPage<Grade> pageResult = gradeService.getGradeByOpr(page, gradeName);

        return ResultUtils.success(pageResult);
    }

}
