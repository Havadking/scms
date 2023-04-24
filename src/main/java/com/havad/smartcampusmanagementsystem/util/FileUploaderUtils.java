package com.havad.smartcampusmanagementsystem.util;

import org.apache.commons.io.filefilter.SuffixFileFilter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @program: SmartCampusManagementSystem
 * @description: 文件上传的工具类
 * @author: Havad
 * @create: 2023-04-25 01:59
 **/

public class FileUploaderUtils {
    // 存储失败的信息
    private static Map<String, Object> errorResults = new HashMap<>();
    // 存储上传的结果信息
    private static Map<String, Object> uploadResults = new HashMap<>();

    /**
     * @param photo 上传的头像图片
     * @param path 图片的保存路径
     * @return 上传的结果
     * @description: 验证上传头像的各种信息,以此来实现对图片大小的控制
     */
    private static Map<String, Object> avatarUpload(MultipartFile photo, String path){

        // 设置上传图片的最大大小为30MB
        int MAX_SIZE = 30 * 1024 * 1024;

        // 获取图片的名字
        String photoName = photo.getOriginalFilename();

        // 创建文件的保存路径
        File filePath = new File(path);
        if (!filePath.exists()){
            filePath.mkdir();
        }

        // 验证文件大小
        if (photo.getSize() > MAX_SIZE){
            errorResults.put("success", false);
            errorResults.put("msg", "上传的图片大小不能超过30MB捏!");
            return errorResults;
        }

        // 验证文件类型
        String[] filter = new String[]{".png", ".PNG", ".jpg", ".JPG", ".jpeg", ".JPEG", ".gif", ".GIF", ".bmp", ".BMP"};
        SuffixFileFilter suffixFileFilter = new SuffixFileFilter(filter);
        if (!suffixFileFilter.accept(new File(path+photoName))){
            errorResults.put("success", false);
            errorResults.put("msg", "不支持该文件类型!请选择图片文件重新上传!");
            return errorResults;
        }

        return null;
    }

    /**
     * @param photo 上传的图片
     * @param dirPath 文件夹路径
     * @param portraitPath 存储头像的路径
     * @return 上传的结果
     * @description: 获取头像的上传结果的信息
     */
    public static Map<String, Object> uploadResult(MultipartFile photo, String dirPath, String portraitPath){
        // 上传头像成功
        if (!photo.isEmpty() && photo.getSize()>0){
            String photoName = photo.getOriginalFilename();
            Map<String, Object> errorResults = FileUploaderUtils.avatarUpload(photo, dirPath);
            if (errorResults != null){
                // 上传失败,返回失败信息
                return errorResults;
            }

            // 使用UUID重命名图片的名称
            String newPhotoName = UUID.randomUUID() + "___" + photoName;
            // 将上传的图片保存在目标目录下
            try {
                photo.transferTo(new File(dirPath + newPhotoName));
                uploadResults.put("success", true);
                // 将存储头像的路径返回给页面
                uploadResults.put("portraitPath", portraitPath + newPhotoName);
            }catch (IOException e){
                e.printStackTrace();
                uploadResults.put("success", false);
                uploadResults.put("msg", "服务的发生异常!上传头像失败!请重试!");
                return uploadResults;
            }
        }else {
            // 上传头像失败
            uploadResults.put("success", false);
            uploadResults.put("msg", "上传头像失败!请重试!");
        }
        return uploadResults;
    }
}
