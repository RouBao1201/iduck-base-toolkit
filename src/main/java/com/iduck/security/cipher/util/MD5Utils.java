package com.iduck.security.cipher.util;

import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加解密工具类
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/11/27
 **/
public class MD5Utils {
    private static final Logger log = LoggerFactory.getLogger(MD5Utils.class);

    private static final String MESSAGE_DIGEST = "MD5";

    private final String salt;

    /**
     * 加密
     *
     * @param str 原密码
     * @return 密码密文
     */
    public String encrypt(String str) {
        if (StrUtil.isEmpty(str)) {
            log.error("MD5Utils => The encryption password cannot be empty.");
            return "";
        }

        // 使用指定的算法名称创建消息摘要。可选项有 MD5  SHA-1  SHA-256
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(MESSAGE_DIGEST);
        } catch (NoSuchAlgorithmException e) {
            log.error("MD5Utils => Encrypt is error[NoSuchAlgorithmException]. return default cipher is empty. ErrorMessage:{}", e.getMessage());
            return "";
        }

        // 使用指定的字节更新摘要（原密码+盐值）
        md.update((str + salt).getBytes());

        //  产生用于生成的哈希值的字节数组。
        byte[] md5Bytes = md.digest();
        return byteArrayToHex(md5Bytes);
    }

    /**
     * 校验密码是否正确
     *
     * @param oldCipher 旧密码密文
     * @param newStr    新密码原文
     * @return 校验结果
     */
    public boolean verify(String oldCipher, String newStr) {
        String newEncrypt = encrypt(newStr);
        if (StrUtil.isEmpty(newEncrypt) || StrUtil.isEmpty(oldCipher)) {
            return false;
        }
        return newEncrypt.equals(oldCipher);
    }

    private String byteArrayToHex(byte[] byteArray) {
        // 首先初始化一个字符数组，用来存放每个16进制字符
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'b', 'd', 'e', 'f'};

        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））
        char[] resultCharArray = new char[byteArray.length * 2];

        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        // 字符数组组合成字符串返回
        return new String(resultCharArray);
    }

    public MD5Utils(String salt) {
        this.salt = salt;
    }

}
