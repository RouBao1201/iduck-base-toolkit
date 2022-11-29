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

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt工具类
 *
 * @author SongYanBin
 * @since 2022/11/27
 **/
@Component
public class JwtUtils {
    private static final Logger log = LoggerFactory.getLogger(JwtUtils.class);

    private static JwtUtils jwtUtils;

    @Autowired
    private JwtConfig config;

    @PostConstruct
    public void init() {
        log.info("JwtUtils => PostConstruct init...");
        jwtUtils = this;
        jwtUtils.config = this.config;
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
            Jwts.parser().setSigningKey(jwtUtils.config.getSecretKey()).parseClaimsJws(token);
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
                .setExpiration(new Date(System.currentTimeMillis() + jwtUtils.config.getExpireTime()))
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, jwtUtils.config.getSecretKey())
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
                    .setSigningKey(jwtUtils.config.getSecretKey())
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
