package com.iduck.exception.util;

import com.iduck.exception.enums.ExceptionEnum;
import com.iduck.exception.model.BaseException;
import com.iduck.exception.model.BusinessException;
import com.iduck.exception.model.ValidException;

/**
 * 异常处理类
 *
 * @author songYanBin
 * @since 2022/11/24
 */
public class ExceptionHandler {

    /**
     * 抛出参数校验异常
     */
    public static void pushValid() {
        push(ExceptionEnum.VALID_EXCEPTION.getCode(), ExceptionEnum.VALID_EXCEPTION.getMessage(), ValidException.class);
    }

    /**
     * 抛出业务异常
     */
    public static void pushBusiness() {
        push(ExceptionEnum.BUSI_EXCEPTION.getCode(), ExceptionEnum.BUSI_EXCEPTION.getMessage(), BusinessException.class);
    }

    /**
     * 抛出权限异常
     */
    public static void pushAuth() {
        push(ExceptionEnum.AUTH_EXCEPTION.getCode(), ExceptionEnum.AUTH_EXCEPTION.getMessage(), BusinessException.class);
    }

    /**
     * 抛出异常
     *
     * @param code     异常编码
     * @param localMsg 异常自定义信息
     * @param clazz    异常类class
     * @param <T>      异常类 extends BaseException
     */
    public static <T extends BaseException> void push(String code, String localMsg, Class<T> clazz) {
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
            return (T) new BaseException();
        }
        return t;
    }

    private ExceptionHandler() {

    }
}
