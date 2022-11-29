package com.iduck.security.jwt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Jwt配置（密钥、有效时间）
 *
 * @author SongYanBin
 * @Copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/11/27
 **/
@Component
public class JwtConfig {

    @Value("${jwt.config.secret-key:jwt-secret-iduck}")
    private String secretKey;

    @Value("${jwt.config.expire-time:0}")
    private Integer expireTime;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Integer getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Integer expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "JwtConfig{" +
                "secretKey='" + secretKey + '\'' +
                ", expireTime=" + expireTime +
                '}';
    }
}
