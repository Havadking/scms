package com.havad.smartcampusmanagementsystem.util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * @program: SmartCampusManagementSystem
 * @description: 验证码实现类
 * @author: Havad
 * @create: 2023-04-23 01:18
 **/


public class ImgVerifyCodeUtils {

    // 验证码的长宽以及字体大小
    private static final int HEIGHT = 30;
    private static final int WIDTH = 80;
    private static final int FONT_SIZE = 15;

    // 存储验证码和验证码图片
    private static char[] verifyCode;
    private static BufferedImage verifyImage;


    /**
     * @return BufferedImage
     * @description: 生成验证码图片
     */
    public static BufferedImage getVerifyImage() {
        // 创建一个图片
        verifyImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_BGR);
        Graphics graphics = verifyImage.getGraphics();

        // 获取一串随机的验证码字符
        verifyCode = generateVerifyCode();

        // 绘制验证码图片的背景
        drawBackground(graphics);

        // 在验证码图片上绘制验证码
        drawVerifyCode(graphics, verifyCode);

        graphics.dispose();

        return verifyImage;
    }

    /**
     * @param graphics   图片
     * @param verifyCode 验证码
     * @description: 绘制验证码
     */
    private static void drawVerifyCode(Graphics graphics, char[] verifyCode) {
        // 设置字体样式和字体大小
        graphics.setFont(new Font("Console", Font.BOLD, FONT_SIZE));
        for (int i = 0; i < verifyCode.length; i++) {
            graphics.setColor(getRandomColor());
            graphics.drawString("" + verifyCode[i], i * FONT_SIZE + 10, 20);
        }

    }

    /**
     * @param graphics 图片
     * @description: 生成严重码图片的背景
     */
    private static void drawBackground(Graphics graphics) {
        // 将当前字体设置为白色
        graphics.setColor(Color.white);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        // 计算随机绘制的座标，绘制大小为2x2的无规则图像200个
        for (int i = 0; i < 200; i++) {
            int x = (int) (Math.random() * WIDTH);
            int y = (int) (Math.random() * HEIGHT);
            // 设置绘制的背景不规则图像的颜色为随机的颜色
            graphics.setColor(getRandomColor());
            graphics.drawOval(x, y, 1, 1);
        }
    }

    /**
     * @return Color
     * @description: 获取一个随机的颜色
     */
    private static Color getRandomColor() {
        return new Color(new Random().nextInt(200), new Random().nextInt(200), new Random().nextInt(200));
    }

    /**
     * @return char[]
     * @description: 随机生成验证码字符
     */
    private static char[] generateVerifyCode() {
        String allAlpha = "0123456789" + "abcdefghijklmnopqrstuvwxyz" + "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        char[] randomAlpha = new char[4];
        for (int i = 0; i < 4; i++) {
            int random = (int) (Math.random() * (26 * 2 + 10));
            randomAlpha[i] = allAlpha.charAt(random);
        }
        return randomAlpha;
    }
}
