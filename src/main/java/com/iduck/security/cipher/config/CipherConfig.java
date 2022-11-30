package com.iduck.security.cipher.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 密码key配置类
 *
 * @author SongYanBin
 * @Copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/11/27
 **/
@Component
public class CipherConfig {
    @Value("${cipher.config.aes.key:r9XMC8xq2XoN/Vni5m/xVQ==}")
    private String aesKey;

    @Value("${cipher.config.md5.salt:iduck-md5-salt}")
    private String md5Salt;

    @Value("${cipher.config.rsa.private-key:''}")
    private String rsaPrivateKey;

    @Value("${cipher.config.rsa.public-key:''}")
    private String rsaPublicKey;


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

    public String getRsaPrivateKey() {
        return rsaPrivateKey;
    }

    public void setRsaPrivateKey(String rsaPrivateKey) {
        this.rsaPrivateKey = rsaPrivateKey;
    }

    public String getRsaPublicKey() {
        return rsaPublicKey;
    }

    public void setRsaPublicKey(String rsaPublicKey) {
        this.rsaPublicKey = rsaPublicKey;
    }
}
