package com.iduck.redis.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * redis存储方法返回值
 * 自定义注解
 *
 * @author JieN
 * @Copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/11/28
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface IRedisCache {
    /**
     * 存入redis的key
     */
    String key() default Key.DEFAULT_KEY;

    /**
     * 到期时间（单位：秒）,默认不到期
     */
    long expire() default -1;

    /**
     * 获取类型
     */
    ObtainType obtain() default ObtainType.REFRESH;

    enum ObtainType {
        ONLY_REDIS,
        REFRESH,
        ABSENT;

        ObtainType() {
        }
    }

    class Key {
        public static final String DEFAULT_KEY = "iduck:IRedisCache:defaultKey:";
    }
}
