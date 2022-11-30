package com.iduck.thread.util;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.iduck.thread.config.ThreadPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 *
 * @author SongYanBin
 * @Copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/11/30
 */
@Component
public class IThreadPoolUtils {
    private static final Logger log = LoggerFactory.getLogger(IThreadPoolUtils.class);

    private static ThreadPoolExecutor threadPool = null;

    private static IThreadPoolUtils iThreadPoolUtils;

    @PostConstruct
    public void init() {
        log.info("IThreadPoolUtils => PostConstruct init...");
        iThreadPoolUtils = this;
        iThreadPoolUtils.config = this.config;
    }

    @Autowired
    private ThreadPoolConfig config;

    /**
     * 执行线程逻辑
     *
     * @param runnable runnable
     */
    public static void execute(Runnable runnable) {
        getThreadPool().execute(runnable);
    }

    /**
     * 执行线程逻辑
     *
     * @param callable callable
     * @param <T>      T
     * @return 返回值
     */
    public static <T> Future<T> submit(Callable<T> callable) {
        return getThreadPool().submit(callable);
    }

    /**
     * 获取线程池
     *
     * @return ThreadPoolExecutor
     */
    private static synchronized ThreadPoolExecutor getThreadPool() {
        if (threadPool == null) {
            // 核心线程数、最大线程数、闲置线程存活时间、时间单位、线程队列、线程工厂、当前线程数已经超过最大线程数时的异常处理策略
            threadPool = new ThreadPoolExecutor(iThreadPoolUtils.config.getCorePoolSize(),
                    iThreadPoolUtils.config.getMaximumPoolSize(),
                    iThreadPoolUtils.config.getKeepAliveTime(),
                    TimeUnit.MILLISECONDS,
                    new LinkedBlockingDeque<>(iThreadPoolUtils.config.getBlockingQueueLength()),
                    new ThreadFactoryBuilder().setNamePrefix(iThreadPoolUtils.config.getPoolPrefixName() + "-%d").build(),
                    new ThreadPoolExecutor.AbortPolicy() {
                        @Override
                        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
                            log.warn("The thread explodes. the total of threads: {}; The active threads: {}; The waiting queue is full,The waiting tasks: {}",
                                    e.getPoolSize(),
                                    e.getActiveCount(),
                                    e.getQueue().size());
                        }
                    });
            log.info("IThreadPoolUtils => Init threadPoolExecutor prefixName:{}, coreSize:{}, maxSize:{}, keepAliveTime:{}ms, blockQueueLength:{}.",
                    iThreadPoolUtils.config.getPoolPrefixName(),
                    iThreadPoolUtils.config.getCorePoolSize(),
                    iThreadPoolUtils.config.getMaximumPoolSize(),
                    iThreadPoolUtils.config.getKeepAliveTime(),
                    iThreadPoolUtils.config.getBlockingQueueLength());
        }
        return threadPool;
    }

    private IThreadPoolUtils() {
    }
}