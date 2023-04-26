package com.havad.smartcampusmanagementsystem.controller;

import com.havad.smartcampusmanagementsystem.pojo.Admin;
import com.havad.smartcampusmanagementsystem.pojo.LoginIn;
import com.havad.smartcampusmanagementsystem.pojo.Student;
import com.havad.smartcampusmanagementsystem.pojo.Teacher;
import com.havad.smartcampusmanagementsystem.service.AdminService;
import com.havad.smartcampusmanagementsystem.service.StudentService;
import com.havad.smartcampusmanagementsystem.service.TeacherService;
import com.havad.smartcampusmanagementsystem.util.ImgVerifyCodeUtils;
import com.havad.smartcampusmanagementsystem.util.JwtUtils;
import com.havad.smartcampusmanagementsystem.util.ResultUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.sql.Struct;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: SmartCampusManagementSystem
 * @description: 整个系统中, 跟其他对象无关的Controller
 * @author: Havad
 * @create: 2023-04-25 23:45
 **/

@Api(tags = "系统需求实现")
@RestController
@RequestMapping("/sms/system")
public class ScmsController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private StudentService studentService;


    @ApiOperation("生成验证码图片")
    @GetMapping("/getVerifyCodeImage")
    public void getVerifyImage(HttpServletRequest request, HttpServletResponse response){
        // 获取验证码图片
        BufferedImage verifyImage = ImgVerifyCodeUtils.getVerifyImage();

        // 获取验证码
        char[] codes = ImgVerifyCodeUtils.getVerifyCode();
        String verifyCodes = new String(codes);

        // 将验证码文本放入session，以供给下次验证
        HttpSession session = request.getSession();
        session.setAttribute("verifyCode", verifyCodes);

        // 将图片相应给浏览器
        try {
            ImageIO.write(verifyImage, "jpg", response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @ApiOperation("登录功能")
    @PostMapping("/login")
    // 将Json自动解析
    public ResultUtils login(@RequestBody LoginIn loginInfo, HttpServletRequest request){
        // 判断验证码是否正确
        HttpSession session = request.getSession();
        String verifyCode = session.getAttribute("verifyCode").toString();
        String verifyCodeByUser = loginInfo.getVerifyCode();
        if (verifyCode.equals("") || verifyCode == null) {
            return ResultUtils.fail().msg("验证码失效！请重试！");
        }
        if (!verifyCode.equalsIgnoreCase(verifyCodeByUser)) {
            return ResultUtils.fail().msg("验证码有误！请仔细输入！");
        }

        // 验证码通过，从session中移除验证码
        session.removeAttribute("verifyCode");

        // 判断用户类型
        Map<String, Object> dataForReturn = new HashMap<>();
        switch (loginInfo.getUserType()) {
            case 1:
                // 管理员
                try {
                    Admin admin = adminService.login(loginInfo);
                    if (null!=admin) {
                        // 将用户的id和类型，以token的形式发给客户端
                        String token = JwtUtils.createNewToken(Long.valueOf(admin.getId()), 1);
                        dataForReturn.put("token", token);
                    }else {
                        throw new RuntimeException("用户名或密码有误！");
                    }
                    // 返回一个成功的对象
                    return ResultUtils.success(dataForReturn);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    // 返回失败的对象
                    return ResultUtils.fail().msg(e.getMessage());
                }
            case 2:
                // 学生
                try {
                    Student student = studentService.login(loginInfo);
                    if (null!=student) {
                        // 将用户的id和类型，以token的形式发给客户端
                        String token = JwtUtils.createNewToken(Long.valueOf(student.getId()), 2);
                        dataForReturn.put("token", token);
                    }else {
                        throw new RuntimeException("用户名或密码有误！");
                    }
                    // 返回一个成功的对象
                    return ResultUtils.success(dataForReturn);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    // 返回失败的对象
                    return ResultUtils.fail().msg(e.getMessage());
                }
            case 3:
                // 教师
                try {
                    Teacher teacher = teacherService.login(loginInfo);
                    if (null!=teacher) {
                        // 将用户的id和类型，以token的形式发给客户端
                        String token = JwtUtils.createNewToken(Long.valueOf(teacher.getId()), 3);
                        dataForReturn.put("token", token);
                    }else {
                        throw new RuntimeException("用户名或密码有误！");
                    }
                    // 返回一个成功的对象
                    return ResultUtils.success(dataForReturn);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    // 返回失败的对象
                    return ResultUtils.fail().msg(e.getMessage());
                }
        }
        return ResultUtils.fail().msg("查无此人！请重新输入！");
    }














}
