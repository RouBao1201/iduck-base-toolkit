package com.iduck.security.cipher.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 密码key配置类
 *
 * @author SongYanBin
 * @since 2022/11/27
 **/
@Component
public class CipherConfig {
    @Value("${cipher.config.secret-key.aes-key:r9XMC8xq2XoN/Vni5m/xVQ==}")
    private String aesKey;

    @Value("${cipher.config.secret-key.md5-salt:iduck-md5-salt}")
    private String md5Salt;

    @Value("${cipher.config.secret-key.rsa-key:iduck-rsa-key}")
    private String rsaKey;

    public String getAesKey() {
        return aesKey;
    }

    public void setAesKey(String aesKey) {
        this.aesKey = aesKey;
    }

    public String getMd5Salt() {
        return md5Salt;
    }

    public void setMd5Salt(String md5Salt) {
        this.md5Salt = md5Salt;
    }

    public String getRsaKey() {
        return rsaKey;
    }

    public void setRsaKey(String rsaKey) {
        this.rsaKey = rsaKey;
    }
}
