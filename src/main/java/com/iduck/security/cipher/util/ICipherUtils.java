package com.iduck.security.cipher.util;

import com.iduck.common.util.ISpringContextHolder;
import com.iduck.security.cipher.config.CipherConfig;

/**
 * 密码统一调度工具类
 *
 * @author SongYanBin
 * @since 2022/11/27
 **/
public class ICipherUtils {

    private static final MD5Utils MD5_UTILS;

    private static final AESUtils AES_UTILS;

    private static final RSAUtils RSA_UTILS;

    private static final CipherConfig CIPHER_CONFIG;

    static {
        CIPHER_CONFIG = ISpringContextHolder.getBean(CipherConfig.class);
        MD5_UTILS = new MD5Utils(CIPHER_CONFIG.getMd5Salt());
        AES_UTILS = new AESUtils(CIPHER_CONFIG.getAesKey());
        RSA_UTILS = new RSAUtils(CIPHER_CONFIG.getRsaKey());
    }

    public static MD5Utils MD5() {
        return MD5_UTILS;
    }

    public static AESUtils AES() {
        return AES_UTILS;
    }

    public static RSAUtils RSA() {
        return RSA_UTILS;
    }

    private ICipherUtils() {

    }
}
