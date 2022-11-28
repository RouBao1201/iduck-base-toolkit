package com.iduck.exception.annotation;


import java.lang.annotation.*;


/**
 * 异常捕获自定义注解
 *
 * @author JieN
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
