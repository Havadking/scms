package com.havad.smartcampusmanagementsystem.util;

import com.mysql.cj.util.StringUtils;
import io.jsonwebtoken.*;

import java.util.Date;

/**
 * @program: SmartCampusManagementSystem
 * @description: 生成token口令
 * @author: Havad
 * @create: 2023-04-25 01:24
 **/

public class JwtUtils {
    // 设置token有效时间为一天,单位为毫秒
    private static final long tokenExpirationTime = 24*60*60*1000;
    // 设置可以对token解密的key
    private static final String secretKey = "havadking";

    /**
     * @description: 生成token字符串
     * @param userId 用户ID
     * @param userType 用户类型
     * @return token字符串
     */
    public static String createNewToken(Long userId, Integer userType){
        return Jwts.builder()
                .setSubject("Havad-USER")
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpirationTime))
                .claim("UserId", userId)
                .claim("UserType", userType)
                 .signWith(SignatureAlgorithm.HS512, secretKey)
                .compressWith(CompressionCodecs.GZIP)
                .compact();
    }

    /**
     * @description: 从token中获取用户类型
     * @return 用户类型
     */
    public static Integer getUserType(String token){
        if (StringUtils.isNullOrEmpty(token)){
            return null;
        }
        Jws<Claims> jwsClaims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);
        Claims claims = jwsClaims.getBody();
        return (Integer) claims.get("UserType");
    }

    /**
     * @description: 解析token中的用户ID
     * @param token 要解析的token
     * @return token中用户的id
     */
    public static Long getUserId(String token){
        if (StringUtils.isNullOrEmpty(token)){
            return null;
        }
        Jws<Claims> jwsClaims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token);
        Claims claims = jwsClaims.getBody();
        return (Long) claims.get("UserId");
    }


    /**
     * @description: 检查token是否可以继续使用
     * @param token 要解析的token
     * @return 是否过期
     */
    public static boolean isOutOfTime(String token){
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody()
                    .getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * @param token 要刷新的token
     * @return 刷新后的token
     * @description: 刷新token
     */
    public String tokenRefresh(String token){
        String newToken;
        try {
            final Claims claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
            newToken = JwtUtils.createNewToken(getUserId(token), getUserType(token));
        } catch (Exception e) {
            newToken = null;
        }
        return newToken;
    }
}
