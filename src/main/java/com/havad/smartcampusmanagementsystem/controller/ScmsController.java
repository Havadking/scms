package com.havad.smartcampusmanagementsystem.controller;

import com.havad.smartcampusmanagementsystem.util.ImgVerifyCodeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @program: SmartCampusManagementSystem
 * @description: 整个系统中, 跟其他对象无关的Controller
 * @author: Havad
 * @create: 2023-04-25 23:45
 **/

@RestController
@RequestMapping("/sms/system")
public class ScmsController {


    @GetMapping("/getVerifiCodeImage")
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
}
