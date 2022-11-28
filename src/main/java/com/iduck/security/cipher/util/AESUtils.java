package com.iduck.security.cipher.util;

import cn.hutool.core.util.StrUtil;
import com.iduck.common.constant.NumberConst;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加解密工具类
 *
 * @author SongYanBin
 * @since 2022/11/27
 **/
public class AESUtils {
    private static final Logger log = LoggerFactory.getLogger(AESUtils.class);

    private final String key;

    private static final String AES = "AES";

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
        if (StrUtil.isEmpty(cipherStr) || StrUtil.isEmpty(clearStr)) {
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
        if (StrUtil.isEmpty(clearStr) || StrUtil.isEmpty(key) || key.length() != NumberConst.SIXTEEN) {
            log.error("AESUtils => Encrypt str cannot be empty and the key length must be 16.");
            return "";
        }
        byte[] raw = key.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, AES);
        byte[] encrypted = new byte[0];
        try {
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
            encrypted = cipher.doFinal(clearStr.getBytes());
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
        if (StrUtil.isEmpty(cipherStr) || StrUtil.isEmpty(key) || key.length() != NumberConst.SIXTEEN) {
            log.error("AESUtils => Encrypt str cannot be empty and the key length must be 16.");
            return "";
        }
        byte[] raw = key.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(raw, AES);
        byte[] original = new byte[0];
        try {
            Cipher cipher = Cipher.getInstance(AES);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
            byte[] decodeBase64 = Base64.decodeBase64(cipherStr);
            original = cipher.doFinal(decodeBase64);
        } catch (Exception e) {
            log.error("AESUtils => Decrypt error. ErrorMessage:{}", e.getMessage());
        }
        return new String(original);
    }

    public AESUtils(String key) {
        this.key = key;
    }
}
