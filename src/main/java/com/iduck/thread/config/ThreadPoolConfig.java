package com.iduck.thread.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 线程池配置类
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/11/30
 */
@Component
public class ThreadPoolConfig {
    /**
     * 线程池前缀
     */
    @Value("${thread-pool.config.prefix-name:iduck-pool}")
    private String poolPrefixName;

    /**
     * 等待队列长度
     */
    @Value("${thread-pool.config.block-queue-length:1000}")
    private int blockingQueueLength;

    /**
     * 闲置线程存活时间
     */
    @Value("${thread-pool.config.keep-alvie-time:60000}")
    private long keepAliveTime;

    /**
     * 核心线程数
     */
    @Value("${thread-pool.config.core-pool-size:10}")
    private int corePoolSize;

    /**
     * 最大线程数
     */
    @Value("${thread-pool.config.max-pool-size:20}")
    private int maximumPoolSize;


    public String getPoolPrefixName() {
        return poolPrefixName;
    }

    public void setPoolPrefixName(String poolPrefixName) {
        this.poolPrefixName = poolPrefixName;
    }

    public int getBlockingQueueLength() {
        return blockingQueueLength;
    }

    public void setBlockingQueueLength(int blockingQueueLength) {
        this.blockingQueueLength = blockingQueueLength;
    }

    public long getKeepAliveTime() {
        return keepAliveTime;
    }

    public void setKeepAliveTime(long keepAliveTime) {
        this.keepAliveTime = keepAliveTime;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }
}