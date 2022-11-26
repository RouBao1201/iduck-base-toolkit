package com.iduck.redis.lock;

/**
 * Redis分布式锁
 *
 * @author songYanBin
 * @since 2022/11/22
 */
public interface RedisLock {
    /**
     * 获取redis锁
     *
     * @param timeoutSec 锁失效时间[单位：s]
     * @return 是否成功
     */
    boolean tryLock(long timeoutSec);

    /**
     * 释放redis锁
     */
    void unlock();
}
