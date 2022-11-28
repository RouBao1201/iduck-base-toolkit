package com.iduck.exception.aspect;

import com.iduck.exception.annotation.ExcCatcher;
import com.iduck.response.util.RespBuilder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 自定义注解@ExcCatcher切面
 *
 * @author SongYanBin
 * @since 2022/11/28
 **/
@Aspect
@Component
@Order(1)
public class ExcCatcherAspect {
    private static final Logger log = LoggerFactory.getLogger(ExcCatcherAspect.class);

    @Pointcut("@within(com.iduck.exception.annotation.ExcCatcher)")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object excCatchAdvice(ProceedingJoinPoint pjp) {
        // 获取方法信息
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        String methodName = signature.getName();

        // 获取类信息
        Class<?> aClass = pjp.getTarget().getClass();
        String className = aClass.getName();

        log.info("ExcCatcherAspect => Execute class:[{}]. method:[{}]", className, methodName);

        // 获取类上的注解信息
        ExcCatcher classCatcher = aClass.getAnnotation(ExcCatcher.class);
        Object proceed = null;
        if (classCatcher != null) {
            String errorMsg = classCatcher.errorMsg();
            try {
                proceed = pjp.proceed();
            } catch (Throwable e) {
                log.info("ExcCatcherAspect[class] => Execute error it is returned by default[BaseResp]. ErrorMessage:{}", e.getMessage());
                return RespBuilder.error(errorMsg, null, e.getMessage());
            }
        } else {
            ExcCatcher methodCatcher = signature.getMethod().getAnnotation(ExcCatcher.class);
            String errorMsg = methodCatcher.errorMsg();
            try {
                proceed = pjp.proceed();
            } catch (Throwable e) {
                log.info("ExcCatcherAspect[method] => Execute error it is returned by default[BaseResp]. ErrorMessage:{}", e.getMessage());
                return RespBuilder.error(errorMsg, null, e.getMessage());
            }
        }
        return proceed;
    }
}
