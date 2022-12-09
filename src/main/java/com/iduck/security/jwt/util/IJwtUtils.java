package com.iduck.security.jwt.util;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.iduck.security.jwt.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt工具类
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/11/27
 **/
@Component
public class IJwtUtils {
    private static final Logger log = LoggerFactory.getLogger(IJwtUtils.class);

    private static JwtConfig jwtConfig;

    @Autowired
    public void setJwtConfig(JwtConfig jwtConfig) {
        IJwtUtils.jwtConfig = jwtConfig;
    }

    /**
     * 校验token是否正确
     *
     * @param token token
     * @return 校验结果
     */
    public static boolean verifyToken(String token) {
        return verifyToken(token, jwtConfig.getSecretKey());
    }

    /**
     * 校验token是否正确
     *
     * @param token     token
     * @param secretKey 密钥
     * @return 校验结果
     */
    public static boolean verifyToken(String token, String secretKey) {
        if (StrUtil.isEmpty(token)) {
            return false;
        }

        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 创建token
     *
     * @param claims     claims
     * @param secretKey  密钥
     * @param expireTime 失效时间
     * @return token
     */
    public static String createToken(Map<String, Object> claims, String secretKey, Integer expireTime) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS2256")
                .setSubject("iduck-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * 创建token
     *
     * @param userId 用户id
     * @return token
     */
    public static String createToken(String userId) {
        HashMap<String, Object> claims = new HashMap<>(16);
        claims.put("userId", userId);
        return createToken(claims, jwtConfig.getSecretKey(), jwtConfig.getExpireTime());
    }

    /**
     * 创建token
     *
     * @param userId     用户唯一标识
     * @param secretKey  密钥
     * @param expireTime 失效时间
     * @return token
     */
    public static String createToken(String userId, String secretKey, Integer expireTime) {
        HashMap<String, Object> claims = new HashMap<>(16);
        claims.put("userId", userId);
        return createToken(claims, secretKey, expireTime);
    }

    /**
     * 解析token
     *
     * @param token token
     * @return Claims
     */
    public static Claims parseToken(String token) {
        return parseToken(token, jwtConfig.getSecretKey());
    }

    /**
     * 解析token
     *
     * @param token     token
     * @param secretKey 密钥
     * @return Claims
     */
    public static Claims parseToken(String token, String secretKey) {
        Claims body = null;
        try {
            body = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.error("JwtUtils => Parse token error. ErrorMessage:{}", e.getMessage());
        }
        return body;
    }

    /**
     * 根据token获取userId
     *
     * @param token token
     * @return userId
     */
    public static String getUserIdFromToken(String token) {
        Claims claims = parseToken(token);
        if (ObjUtil.isEmpty(claims)) {
            return "";
        }
        return String.valueOf(claims.get("userId"));
    }

    /**
     * 根据token获取userId
     *
     * @param token     token
     * @param secretKey 密钥
     * @return userId
     */
    public static String getUserIdFromToken(String token, String secretKey) {
        Claims claims = parseToken(token, secretKey);
        if (ObjUtil.isEmpty(claims)) {
            return "";
        }
        return String.valueOf(claims.get("userId"));
    }

    private IJwtUtils() {

    }
}
