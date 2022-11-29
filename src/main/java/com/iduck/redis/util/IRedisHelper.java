package com.iduck.redis.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/**
 * Redis工具类（RedisTemplate进一步封装）
 *
 * @author songYanBin
 * @Copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/11/24
 */
@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Component
public class IRedisHelper {
    private static final Logger log = LoggerFactory.getLogger(IRedisHelper.class);

    private static IRedisHelper redisHelper;

    @PostConstruct
    public void init() {
        log.info("RedisHelper => PostConstruct init...");
        redisHelper = this;
        redisHelper.redisTemplate = this.redisTemplate;
    }

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 设置key-value
     */
    public static void set(String key, Object value) {
        redisHelper.redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置key-value,并指定过期时间
     */
    public static void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        redisHelper.redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 设置key的过期时间
     */
    public static boolean expire(String key, long timeout, TimeUnit unit) {
        return Boolean.TRUE.equals(redisHelper.redisTemplate.expire(key, timeout, unit));
    }

    /**
     * 设置key具体在哪个时间过期
     */
    public static void expireAt(String key, Date date) {
        redisHelper.redisTemplate.expireAt(key, date);
    }

    /**
     * 获取数据
     */
    public static Object get(String key) {
        return redisHelper.redisTemplate.opsForValue().get(key);
    }

    /**
     * 获取数据,指定返回类型
     */
    public static <T> T get(String key, Class<T> clazz) {
        ValueOperations<String, T> value = redisHelper.redisTemplate.opsForValue();
        return value.get(key);
    }

    /**
     * 删除单个key
     */
    public static boolean delete(String key) {
        return Boolean.TRUE.equals(redisHelper.redisTemplate.delete(key));
    }

    /**
     * 判断是否存在key
     */
    public static boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisHelper.redisTemplate.hasKey(key));
    }

    /**
     * 获取所有的key,正则表达式（*代表所有）
     */
    public static Set keys(Pattern pattern) {
        return redisHelper.redisTemplate.keys(pattern);
    }

    /**
     * 修改key的名称
     */
    public static void rename(String oldKey, String newKey) {
        redisHelper.redisTemplate.rename(oldKey, newKey);
    }

    /**
     * 如果存在key,则修改key的名称
     */
    public static void renameIfAbsent(String oldKey, String newKey) {
        redisHelper.redisTemplate.renameIfAbsent(oldKey, newKey);
    }

    /**
     * 根据key获取value类型
     */
    public static DataType type(String key) {
        return redisHelper.redisTemplate.type(key);
    }

    /**
     * 获取某个key的剩余过期时间
     */
    public static long getExpire(String key) {
        return Long.parseLong(String.valueOf(redisHelper.redisTemplate.getExpire(key)));
    }

    /**
     * 获取某个key的剩余过期时间,指定单位
     */
    public static long getExpire(String key, TimeUnit unit) {
        return Long.parseLong(String.valueOf(redisHelper.redisTemplate.getExpire(key, unit)));
    }

    /**
     * 在key原本的value后追加数据
     */
    public static void append(String key, String value) {
        redisHelper.redisTemplate.opsForValue().append(key, value);
    }

    /**
     * 若key存在,则重新设置value值
     */
    public static void setIfAbsent(String key, String value) {
        redisHelper.redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    // TODO 还有很多方法,后续再添加

    private IRedisHelper() {
    }
}
