package com.iduck.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 监控狗自定义注解
 * TODO 后期功能再完善...
 *
 * @author JieN
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/11/28
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface WatchDog {
    /**
     * 运行方式
     */
    RunType runType() default RunType.TIMER;

    /**
     * 耗时时间阈值,超出则抛出异常中断方法
     */
    long threshold() default 0;

    /**
     * 拒绝级别策略
     */
    RejectLevel rejectLevel() default RejectLevel.WARN;

    enum RunType {
        TIMER
    }

    enum RejectLevel {
        WARN,
        ERROR
    }
}
