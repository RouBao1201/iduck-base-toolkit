package com.iduck.redis.lock.impl;

import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.IdUtil;
import com.iduck.redis.lock.IRedisLock;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 简单redis分布式锁
 *
 * @author songYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/11/22
 */
public class ISimpleRedisLock implements IRedisLock {

    private static final String KEY_PREFIX = "simple-lock:";

    private static final String ID_PREFIX = IdUtil.simpleUUID() + "-";

    private String key;

    private final StringRedisTemplate stringRedisTemplate;

    private ISimpleRedisLock(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean tryLock(long timeoutSec) {
        String redisKey = KEY_PREFIX + this.key;
        String redisValue = ID_PREFIX + Thread.currentThread().getId();
        Boolean isSuccess = stringRedisTemplate.opsForValue()
                .setIfAbsent(redisKey, redisValue, timeoutSec, TimeUnit.SECONDS);
        return BooleanUtil.isTrue(isSuccess);
    }

    @Override
    public void unlock() {
        String redisKey = KEY_PREFIX + this.key;
        String redisValue = ID_PREFIX + Thread.currentThread().getId();
        String val = stringRedisTemplate.opsForValue().get(redisKey);
        if (redisValue.equals(val)) {
            stringRedisTemplate.delete(redisKey);
        }
    }
}
