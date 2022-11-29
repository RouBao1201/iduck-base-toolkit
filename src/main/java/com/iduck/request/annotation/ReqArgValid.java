package com.iduck.request.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求参数校验自定义注解
 *
 * @author JieN
 * @since 2022/11/28
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ReqArgValid {
    /**
     * 校验失败返回信息
     */
    String errorMsg() default "参数异常";

    /**
     * 执行方法名称,写在请求参数体里,并且必须为无参、返回值为boolean
     */
    String[] method() default {};
}
