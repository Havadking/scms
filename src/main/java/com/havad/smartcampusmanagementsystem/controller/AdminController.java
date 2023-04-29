package com.havad.smartcampusmanagementsystem.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.havad.smartcampusmanagementsystem.pojo.Admin;
import com.havad.smartcampusmanagementsystem.service.AdminService;
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
 * @description: 管理员Controller层
 * @author: Havad
 * @create: 2023-04-25 23:31
 **/

@Api(tags = "管理员需求实现")
// 异步交互
@RestController
// 请求路径
@RequestMapping("/sms/adminController")
public class AdminController {

    @Autowired
    private AdminService adminService;


    @ApiOperation("分页带条件查询Admin信息")
    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public ResultUtils getAllAdmin(
            @ApiParam("页码") @PathVariable("pageNo") Integer pageNo,
            @ApiParam("页大小") @PathVariable("pageSize") Integer pageSize,
            @ApiParam("管理员名字用来查询") String adminName
    ){
        Page<Admin> page = new Page<>(pageNo, pageSize);

        IPage<Admin> iPage = adminService.getAdminByOpr(page, adminName);

        return ResultUtils.success(iPage);
    }



    @ApiOperation("新增或修改Admin信息")
    @PostMapping("/saveOrUpdateAdmin")
    public ResultUtils saveOrUpdateAdmin(
            @ApiParam("Json格式的Admin对象")
            @RequestBody Admin admin
    ){
        Integer id = admin.getId();
        if (id == null || id == 0){
            admin.setPassword(MD5Utils.md5Encrypt(admin.getPassword()));
        }

        adminService.saveOrUpdate(admin);

        return ResultUtils.success();
    }

    @ApiOperation("删除管理员信息的功能")
    @DeleteMapping("/deleteAdmin")
    public ResultUtils deleteAdmin(
            @ApiParam("要删除的id集合的json格式")
            @RequestBody List<Integer> list
    ){
        adminService.removeByIds(list);

        return ResultUtils.success();

    }


}
