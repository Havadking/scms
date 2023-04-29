package com.havad.smartcampusmanagementsystem.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.havad.smartcampusmanagementsystem.pojo.Admin;
import com.havad.smartcampusmanagementsystem.pojo.LoginForm;
import com.havad.smartcampusmanagementsystem.pojo.Student;
import com.havad.smartcampusmanagementsystem.pojo.Teacher;
import com.havad.smartcampusmanagementsystem.service.AdminService;
import com.havad.smartcampusmanagementsystem.service.StudentService;
import com.havad.smartcampusmanagementsystem.service.TeacherService;
import com.havad.smartcampusmanagementsystem.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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




    @ApiOperation("修改密码的功能")
    @PostMapping("/updatePwd/{oldPwd}/{newPwd}")
    public ResultUtils updatePassword(
            @ApiParam("token") @RequestHeader("token") String token,
            @ApiParam("原密码") @PathVariable("oldPwd") String oldPwd,
            @ApiParam("新密码") @PathVariable("newPwd") String newPwd
    ){
        boolean outOfTime = JwtUtils.isOutOfTime(token);
        if (outOfTime){
            // 如果token过期了
            return ResultUtils.fail().message("登录信息（token）失效，请重新登录");
        }
        // 获取用户id和类型
        Integer userType = JwtUtils.getUserType(token);
        Long userId = JwtUtils.getUserId(token);

        oldPwd = MD5Utils.md5Encrypt(oldPwd);
        newPwd = MD5Utils.md5Encrypt(newPwd);

        switch (userType) {
            case 1:
                QueryWrapper<Admin> queryWrapperAdmin = new QueryWrapper<>();
                queryWrapperAdmin.eq("id", userId.intValue());
                queryWrapperAdmin.eq("password", oldPwd);
                Admin admin = adminService.getOne(queryWrapperAdmin);
                if (admin != null){
                    admin.setPassword(newPwd);
                    adminService.saveOrUpdate(admin);
                }else {
                    return ResultUtils.fail().message("密码输入错误");
                }
                break;

            case 2:
                QueryWrapper<Student> queryWrapperStudent = new QueryWrapper<>();
                queryWrapperStudent.eq("id", userId.intValue());
                queryWrapperStudent.eq("password", oldPwd);
                Student student = studentService.getOne(queryWrapperStudent);
                if (student != null){
                    student.setPassword(newPwd);
                    studentService.saveOrUpdate(student);
                }else {
                    return ResultUtils.fail().message("密码输入错误");
                }
                break;

            case 3:
                QueryWrapper<Teacher> queryWrapperTeacher = new QueryWrapper<>();
                queryWrapperTeacher.eq("id", userId.intValue());
                queryWrapperTeacher.eq("password", oldPwd);
                Teacher teacher = teacherService.getOne(queryWrapperTeacher);
                if (teacher != null){
                    teacher.setPassword(newPwd);
                    teacherService.saveOrUpdate(teacher);
                }else {
                    return ResultUtils.fail().message("密码输入错误");
                }
                break;
        }
        return ResultUtils.success();
    }










    @ApiOperation("头像上传功能的实现")
    @PostMapping("/headerImgUpload")
    public ResultUtils headerImgUpload(
            @ApiParam("图片转换为MultipartFile形式的参数")
            @RequestPart("multipartFile")
            MultipartFile multipartFile
    ){

        // 1.重命名文件
        String newName = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String originalFilename = multipartFile.getOriginalFilename();
        assert originalFilename != null;
        String substring = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFilename =  newName + substring;
        System.out.println(newFilename);

        // 2.保存文件
        // 将文件发送到独立的图片服务器，然后获取其url
        // 为了方便设计，我假设该图片服务器就在/public/upload
        String portraitPath = "F:\\javaspace\\SmartCampusManagementSystem\\target\\classes\\public\\upload\\".concat(newFilename);
        System.out.println(portraitPath);
        try {
            multipartFile.transferTo(new File(portraitPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 返回文件的保存路径
        String path = "upload/".concat(newFilename);
        return ResultUtils.success(path);
    }



    @ApiOperation("从token中获取信息")
    @GetMapping("/getInfo")
    public ResultUtils getInfoByToken(@ApiParam("token的String类型的字符串") @RequestHeader("token") String token){
        boolean outOfTime = JwtUtils.isOutOfTime(token);
        if (outOfTime){
            // 过期了
            return ResultUtils.build(null, ResultCodeEnum.TOKEN_ERROR);
        }
        // 从token中解析出用户名和用户类型
        Long userId = JwtUtils.getUserId(token);
        Integer userType = JwtUtils.getUserType(token);

        Map<String, Object> data = new HashMap<>();
        // 判断用户类型
        switch (userType){
            case 1:
                // 管理员
                Admin admin = adminService.getAdminInfoById(userId);
                data.put("userType", 1);
                data.put("user", admin);
                break;
            case 2:
                // 学生
                Student student = studentService.getStudentInfoById(userId);
                data.put("userType", 2);
                data.put("user", student);
                break;
            case 3:
                // 老师
                Teacher teacher = teacherService.getTeacherInfoById(userId);
                data.put("userType", 3);
                data.put("user", teacher);
                break;
        }

        return ResultUtils.success(data);
    }


    @ApiOperation("生成验证码图片")
    @GetMapping("/getVerifiCodeImage")
    public void getVerifyImage(HttpServletRequest request, HttpServletResponse response){
        // 获取验证码图片
        BufferedImage verifyImage = ImgVerifyCodeUtils.getVerifyImage();

        // 获取验证码
        char[] codes = ImgVerifyCodeUtils.getVerifyCode();
        String verifyCodes = String.valueOf(codes);

        // 将验证码文本放入session，以供给下次验证
        HttpSession session = request.getSession();
        session.setAttribute("verifiCode", verifyCodes);

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
    public ResultUtils login(@ApiParam("填写的登录信息的Json格式的表单数据") @RequestBody LoginForm loginInfo, HttpServletRequest request){
        // 判断验证码是否正确
        HttpSession session = request.getSession();
        String verifyCode = String.valueOf(session.getAttribute("verifiCode"));
        System.out.println(verifyCode);
        String verifyCodeByUser = loginInfo.getVerifiCode();
        System.out.println(verifyCodeByUser);
        if ("".equals(verifyCode)) {
            // session过期了，验证码失效
            System.out.println("验证码失效！请重试！");
            return ResultUtils.fail().message("验证码失效！请重试！");

        }
        if (!verifyCode.equalsIgnoreCase(verifyCodeByUser)) {
            // 验证码输入错误了
            System.out.println("验证码有误！请仔细输入！");
            return ResultUtils.fail().message("验证码有误！请仔细输入！");

        }

        // 验证码通过，从session中移除验证码
        session.removeAttribute("verifiCode");
        System.out.println("移除验证码");

        // 判断用户类型
        Map<String, Object> dataForReturn = new HashMap<>();
        System.out.println(loginInfo.getUserType());
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
                    return ResultUtils.fail().message(e.getMessage());
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
                    return ResultUtils.fail().message(e.getMessage());
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
                    return ResultUtils.fail().message(e.getMessage());
                }
        }
        return ResultUtils.fail().message("查无此人！请重新输入！");
    }

}
