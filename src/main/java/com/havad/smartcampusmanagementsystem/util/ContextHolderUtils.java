package com.havad.smartcampusmanagementsystem.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @program: SmartCampusManagementSystem
 * @description: 从request对象中直接获取token对象, 并解析其中的信息
 * @author: Havad
 * @create: 2023-04-25 02:48
 **/

public class ContextHolderUtils {

    /**
     * @param request Http请求
     * @return 用户ID
     * @description: 从请求头中的token中解析用户的id
     */
    public static Long getUserIdFromRequest(HttpServletRequest request) {
        String token = request.getHeader("token");
        return JwtUtils.getUserId(token);
    }

    /**
     * @param request Http请求
     * @return 用户类型
     * @description: 从请求头中的token中解析用户的类型
     */
    public static Integer getUserTypeFromRequest(HttpServletRequest request){
        String token = request.getHeader("token");
        return JwtUtils.getUserType(token);
    }
}
