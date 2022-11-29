package com.iduck.security.cipher.util;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

/**
 * AES加解密工具类
 *
 * @author SongYanBin
 * @Copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/11/27
 **/
public class AESUtils {
    private static final Logger log = LoggerFactory.getLogger(AESUtils.class);

    private final String key;

    /**
     * AES密钥标识
     */
    private static final String SIGN_AES = "AES";

    /**
     * 密码器AES模式
     */
    private static final String CIPHER_AES = "AES/ECB/PKCS5Padding";

    /**
     * 密钥长度128
     */
    public static final int KEY_SIZE_128_LENGTH = 128;

    /**
     * 密钥长度192
     */
    public static final int KEY_SIZE_192_LENGTH = 192;

    /**
     * 密钥长度256
     */
    public static final int KEY_SIZE_256_LENGTH = 256;

    /**
     * 加密
     *
     * @param str 明文
     * @return 密文
     */
    public String encrypt(String str) {
        return encrypt(str, key);
    }

    /**
     * 解密
     *
     * @param cipherStr 密文
     * @return 明文
     */
    public String decrypt(String cipherStr) {
        return decrypt(cipherStr, key);
    }

    /**
     * 校验密文是否正确
     *
     * @param cipherStr 密文
     * @param clearStr  明文
     * @return 校验结果
     */
    public boolean verify(String cipherStr, String clearStr) {
        if (StrUtil.isEmpty(cipherStr) || StrUtil.isEmpty(clearStr)) {
            return false;
        }
        return clearStr.equals(decrypt(cipherStr));
    }

    /**
     * 校验密文是否正确
     *
     * @param cipherStr 密文
     * @param clearStr  明文
     * @param key       密钥
     * @return 校验结果
     */
    public boolean verify(String cipherStr, String clearStr, String key) {
        if (StrUtil.isEmpty(cipherStr)
                || StrUtil.isEmpty(clearStr)
                || StrUtil.isEmpty(key)) {
            return false;
        }
        return clearStr.equals(decrypt(cipherStr, key));
    }

    /**
     * 加密
     *
     * @param clearStr 明文
     * @param key      密钥
     * @return 密文
     */
    public String encrypt(String clearStr, String key) {
        byte[] decodedKey = Base64.decodeBase64(key.getBytes(StandardCharsets.UTF_8));
        SecretKeySpec secretKeySpec = new SecretKeySpec(decodedKey, SIGN_AES);
        byte[] encrypted = new byte[0];
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            encrypted = cipher.doFinal(clearStr.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("AESUtils => Encrypt error.ErrorMessage:{}", e.getMessage());
        }
        return Base64.encodeBase64String(encrypted);
    }

    /**
     * 解密
     *
     * @param cipherStr 密文
     * @param key       密钥
     * @return 明文
     */
    public String decrypt(String cipherStr, String key) {
        byte[] decodedKey = Base64.decodeBase64(key.getBytes(StandardCharsets.UTF_8));
        SecretKeySpec secretKeySpec = new SecretKeySpec(decodedKey, SIGN_AES);
        byte[] original = new byte[0];
        try {
            Cipher cipher = Cipher.getInstance(CIPHER_AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            original = cipher.doFinal(Base64.decodeBase64(cipherStr));
        } catch (Exception e) {
            log.error("AESUtils => Decrypt error. ErrorMessage:{}", e.getMessage());
        }
        return new String(original, StandardCharsets.UTF_8);
    }

    /**
     * 生成密钥,长度:128/192/256
     *
     * @param keySize 密钥长度
     * @return 密钥
     */
    public String generateSecretKey(int keySize) {
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance(SIGN_AES);
        } catch (NoSuchAlgorithmException e) {
            log.error("AESUtils => GenerateSecretKey error. ErrorMessage:{}", e.getMessage());
            return "";
        }
        keyGenerator.init(keySize);
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] keyBytes = secretKey.getEncoded();
        return new String(Base64.encodeBase64(keyBytes), StandardCharsets.UTF_8);
    }

    public AESUtils(String key) {
        this.key = key;
    }
}
