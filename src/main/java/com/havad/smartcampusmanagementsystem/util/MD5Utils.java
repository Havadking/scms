package com.havad.smartcampusmanagementsystem.util;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @program: SmartCampusManagementSystem
 * @description: MD5加密类
 * @author: Havad
 * @create: 2023-04-23 02:43
 **/


public final class MD5Utils {

    public static String encrypt(String data){
        char[] hexCodes = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        byte[] bytes = data.getBytes();

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(bytes);
            bytes = messageDigest.digest();

            char[] codes = new char[2* bytes.length];
            int flag = 0;
            for (byte b : bytes) {
                codes[flag++] = hexCodes[b >>> 4 & 0xf];
                codes[flag++] = hexCodes[b & 0xf];
            }
            return new String(codes);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5加密出错" + e);
        }
    }
}
