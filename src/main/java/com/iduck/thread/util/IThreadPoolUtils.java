package com.iduck.thread.util;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.iduck.thread.config.ThreadPoolConfig;
import com.iduck.thread.config.ThreadPoolRejectedHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * 线程池工具类
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
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
     * @param runnable 线程逻辑
     */
    public static void execute(Runnable runnable) {
        getThreadPool(RunType.EXECUTE, null).execute(runnable);
    }

    /**
     * 执行线程逻辑
     *
     * @param runnable    线程逻辑
     * @param excConsumer 异常回调消费
     */
    public static void execute(Runnable runnable, Consumer<Throwable> excConsumer) {
        getThreadPool(RunType.EXECUTE, excConsumer).execute(runnable);
    }

    /**
     * 执行线程逻辑
     *
     * @param callable callable
     * @param <T>      返回值类型
     * @return 返回值
     */
    public static <T> Future<T> submit(Callable<T> callable) {
        return getThreadPool(RunType.SUBMIT, null).submit(callable);
    }

    /**
     * 执行线程逻辑
     *
     * @param callable    线程逻辑
     * @param excConsumer 异常回调消费
     * @param <T>         返回值类型
     * @return Future<T>
     */
    public static <T> Future<T> submit(Callable<T> callable, Consumer<Throwable> excConsumer) {
        return getThreadPool(RunType.SUBMIT, excConsumer).submit(callable);
    }

    /**
     * 获取活跃的线程数
     *
     * @return 活跃线程数量
     */
    public static int activeCount() {
        return getThreadPool(null, null).getActiveCount();
    }

    /**
     * 调用 shutdown() 方法之后线程池并不是立刻就被关闭,因为这时线程池中可能还有很多任务正在被执行,或是任务队列中有大量正在等待被执行的任务。
     * 调用 shutdown() 方法后线程池会在执行完正在执行的任务和队列中等待的任务后才彻底关闭。
     * 调用 shutdown() 方法后如果还有新的任务被提交，线程池则会根据拒绝策略直接拒绝后续新提交的任务
     */
    public static void shutdown() {
        getThreadPool(null, null).shutdown();
    }

    /**
     * 在执行 shutdownNow 方法之后,首先会给所有线程池中的线程发送 interrupt 中断信号。
     * 尝试中断这些任务的执行,然后会将任务队列中正在等待的所有任务转移到一个 List 中并返回,我们可以根据返回的任务 List 来进行一些补救的操作
     *
     * @return 未执行的任务（可用于补偿）
     */
    public static List<Runnable> shutdownNow() {
        return getThreadPool(null, null).shutdownNow();
    }

    /**
     * 判断线程池是否已经开始了关闭工作。
     * 是否执行了 shutdown 或者 shutdownNow 方法。
     * 并不代表线程池此时已经彻底关闭了,这仅仅代表线程池开始了关闭的流程;也就是说,此时可能线程池中依然有线程在执行任务,队列里也可能有等待被执行的任务。
     */
    public static boolean isShutdown() {
        return getThreadPool(null, null).isShutdown();
    }

    /**
     * 这个方法可以检测线程池是否真正“终结”了,这不仅代表线程池已关闭,同时代表线程池中的所有任务都已经都执行完毕了。
     * 直到所有任务都执行完毕了,调用 isTerminated() 方法才会返回 true,这表示线程池已关闭并且线程池内部是空的,所有剩余的任务都执行完毕了。
     *
     * @return 线程池是否征程终结
     */
    public static boolean isTerminated() {
        return getThreadPool(null, null).isTerminated();
    }

    /**
     * 获取线程池
     *
     * @return ThreadPoolExecutor
     */
    private static synchronized ThreadPoolExecutor getThreadPool(RunType runType, Consumer<Throwable> excConsumer) {
        if (threadPool == null) {
            int corePoolSize = iThreadPoolUtils.config.getCorePoolSize();
            int maximumPoolSize = iThreadPoolUtils.config.getMaximumPoolSize();
            long keepAliveTime = iThreadPoolUtils.config.getKeepAliveTime();
            TimeUnit unit = TimeUnit.MILLISECONDS;
            LinkedBlockingDeque<Runnable> blockingQueue = new LinkedBlockingDeque<>(iThreadPoolUtils.config.getBlockingQueueLength());
            ThreadPoolRejectedHandler.AbortPolicyHandler rejectedPolicy = ThreadPoolRejectedHandler.abortPolicy();
            ThreadFactory threadFactory;

            /*
             * 【submit】 提交和 【execute】 线程任务捕获异常方式不同
             * execute: 线程工厂设置UncaughtExceptionHandler处理异常
             * submit: 重写线程池afterExecute方法处理异常
             */
            if (excConsumer != null && runType == RunType.EXECUTE) {
                // 设置线程工厂异常处理方法(execute执行)
                threadFactory = new ThreadFactoryBuilder()
                        .setNamePrefix(iThreadPoolUtils.config.getPoolPrefixName() + "-")
                        .setUncaughtExceptionHandler((thread, throwable) -> {
                            log.error("IThreadPoolUtils => Thread[{}] execute exception. Run callback method. ErrorMessage:{}.",
                                    thread, throwable.getMessage());
                            excConsumer.accept(throwable);
                        }).build();
            } else {
                threadFactory = new ThreadFactoryBuilder()
                        .setNamePrefix(iThreadPoolUtils.config.getPoolPrefixName() + "-")
                        .build();
            }

            // 创建线程池（重写afterExecute方法,执行submit方法时用于异常回调）
            if (excConsumer != null && runType == RunType.SUBMIT) {
                threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                        unit, blockingQueue, threadFactory, rejectedPolicy) {
                    @Override
                    protected void afterExecute(Runnable r, Throwable t) {
                        if (r instanceof FutureTask<?>) {
                            try {
                                ((FutureTask<?>) r).get();
                            } catch (Exception e) {
                                excConsumer.accept(e);
                            }
                        }
                    }
                };
            } else {
                // 创建线程池（默认方式,执行execute）
                threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime,
                        unit, blockingQueue, threadFactory, rejectedPolicy);
            }
            log.info("IThreadPoolUtils => Init threadPoolExecutor {prefixName:{}, coreSize:{}, maxSize:{}, keepAliveTime:{}ms, blockQueueLength:{}}.",
                    iThreadPoolUtils.config.getPoolPrefixName(),
                    iThreadPoolUtils.config.getCorePoolSize(),
                    iThreadPoolUtils.config.getMaximumPoolSize(),
                    iThreadPoolUtils.config.getKeepAliveTime(),
                    iThreadPoolUtils.config.getBlockingQueueLength());
        }
        return threadPool;
    }

    enum RunType {
        SUBMIT,
        EXECUTE
    }

    private IThreadPoolUtils() {
    }
}