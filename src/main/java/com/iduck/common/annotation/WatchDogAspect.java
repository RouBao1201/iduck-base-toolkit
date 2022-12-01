package com.iduck.common.annotation;

import com.iduck.exception.util.IExceptionHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;

/**
 * 监控狗切面
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/11/30
 **/
@Aspect
@Component
public class WatchDogAspect {
    private static final Logger log = LoggerFactory.getLogger(WatchDogAspect.class);

    @Pointcut("@annotation(com.iduck.common.annotation.WatchDog)")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object watchDogAdvice(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        WatchDog annotation = method.getAnnotation(WatchDog.class);
        Object obj = null;
        if (annotation.runType() == WatchDog.RunType.TIMER) {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            obj = pjp.proceed();
            stopWatch.stop();
            long totalTimeMillis = stopWatch.getTotalTimeMillis();
            long threshold = annotation.threshold();
            WatchDog.RejectLevel rejectLevel = annotation.rejectLevel();
            if (threshold > 0 && totalTimeMillis > threshold) {
                if (rejectLevel == WatchDog.RejectLevel.ERROR) {
                    log.error("WatchDogAspect => The method[{}] exec time: {}, threshold time: {}",
                            method.getName(), totalTimeMillis, threshold);
                    IExceptionHandler.pushBusinessExc("方法耗时超出限制");
                } else if (rejectLevel == WatchDog.RejectLevel.WARN) {
                    log.warn("WatchDogAspect => The method[{}] slow please optimize. method exec time: {}, threshold time: {}",
                            method.getName(), totalTimeMillis, threshold);
                }
            }
            log.info("WatchDogAspect => The method[{}] execution consumption time is: {}ms.",
                    method.getName(), totalTimeMillis);
        }
        return obj;
    }
}
