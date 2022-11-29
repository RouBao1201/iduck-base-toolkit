package com.iduck.exception.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * 异常捕获自定义注解
 *
 * @author JieN
 * @Copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/11/28
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface ExcCatcher {
    /**
     * 失败描述
     */
    String errorMsg() default "业务异常";
}
