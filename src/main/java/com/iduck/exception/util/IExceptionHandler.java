package com.iduck.exception.util;

import com.iduck.exception.enums.ExceptionEnum;
import com.iduck.exception.model.AuthException;
import com.iduck.exception.model.BaseException;
import com.iduck.exception.model.BusinessException;
import com.iduck.exception.model.SecurityException;
import com.iduck.exception.model.ValidException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异常处理类
 *
 * @author songYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/11/24
 */
public class IExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(IExceptionHandler.class);

    /**
     * 抛出参数校验异常
     */
    public static void pushValidExc() {
        pushExc(ExceptionEnum.VALID_EXCEPTION.getCode(), ExceptionEnum.VALID_EXCEPTION.getMessage(), ValidException.class);
    }

    /**
     * 抛出参数校验异常
     *
     * @param message 异常信息
     */
    public static void pushValidExc(String message) {
        pushExc(ExceptionEnum.VALID_EXCEPTION.getCode(), message, ValidException.class);
    }

    /**
     * 抛出业务异常
     */
    public static void pushBusinessExc() {
        pushExc(ExceptionEnum.BUSI_EXCEPTION.getCode(), ExceptionEnum.BUSI_EXCEPTION.getMessage(), BusinessException.class);
    }

    /**
     * 抛出业务异常
     *
     * @param message 异常信息
     */
    public static void pushBusinessExc(String message) {
        pushExc(ExceptionEnum.BUSI_EXCEPTION.getCode(), message, BusinessException.class);
    }

    /**
     * 抛出权限异常
     */
    public static void pushAuthExc() {
        pushExc(ExceptionEnum.AUTH_EXCEPTION.getCode(), ExceptionEnum.AUTH_EXCEPTION.getMessage(), BusinessException.class);
    }

    /**
     * 抛出权限异常
     *
     * @param message 异常信息
     */
    public static void pushAuthExc(String message) {
        pushExc(ExceptionEnum.AUTH_EXCEPTION.getCode(), message, AuthException.class);
    }

    /**
     * 抛出安全异常
     */
    public static void pushSecurityExc() {
        pushExc(ExceptionEnum.SECURITY_EXCEPTION.getCode(), ExceptionEnum.SECURITY_EXCEPTION.getMessage(), SecurityException.class);
    }

    /**
     * 抛出安全异常
     *
     * @param message 异常信息
     */
    public static void pushSecurityExc(String message) {
        pushExc(ExceptionEnum.SECURITY_EXCEPTION.getCode(), message, SecurityException.class);
    }

    /**
     * 抛出异常
     *
     * @param code     异常编码
     * @param localMsg 异常自定义信息
     * @param clazz    异常类class
     * @param <T>      异常类 extends BaseException
     */
    public static <T extends BaseException> void pushExc(String code, String localMsg, Class<T> clazz) {
        T instance = instance(clazz);
        instance.setCode(code);
        instance.setLocalMsg(localMsg);
        throw instance;
    }

    /**
     * 根据class创建对应异常类
     *
     * @param clazz class
     * @param <T>   extends BaseException
     * @return exception
     */
    private static <T extends BaseException> T instance(Class<T> clazz) {
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("ExceptionHandler => Instance error. init baseException to default.");
            return (T) new BaseException();
        }
        return t;
    }

    private IExceptionHandler() {

    }
}
