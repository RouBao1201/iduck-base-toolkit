package com.iduck.security.util;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.iduck.common.util.SpringContextHolder;
import com.iduck.security.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt工具类
 *
 * @author SongYanBin
 * @since 2022/11/27
 **/
public class JwtUtils {
    private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);

    /**
     * 配置类
     * 默认[secretKey:jwt-secret-iduck]、[expireTime:0]
     */
    private static final JwtConfig JWT_CONFIG;

    static {
        JWT_CONFIG = SpringContextHolder.getBean(JwtConfig.class);
        log.info("JwtUtils => Jwt config init:[{}]", JWT_CONFIG);
    }

    /**
     * 校验token是否正确
     *
     * @param token token
     * @return [true：token正确]/[false：token错误]
     */
    public static boolean verifyToken(String token) {
        if (StrUtil.isEmpty(token)) {
            return false;
        }

        try {
            Jwts.parser().setSigningKey(JWT_CONFIG.getSecretKey()).parseClaimsJws(token);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 创建token
     *
     * @param claims claims
     * @return token
     */
    public static String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS2256")
                .setSubject("iduck-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_CONFIG.getExpireTime()))
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, JWT_CONFIG.getSecretKey())
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
        return createToken(claims);
    }

    /**
     * 解析token
     *
     * @param token token
     * @return Claims
     */
    public static Claims parseToken(String token) {
        Claims body = null;
        try {
            body = Jwts.parser()
                    .setSigningKey(JWT_CONFIG.getSecretKey())
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

    private JwtUtils() {

    }
}
