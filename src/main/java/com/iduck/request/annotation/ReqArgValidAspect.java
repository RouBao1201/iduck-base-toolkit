package com.iduck.request.annotation;

import com.iduck.exception.util.IExceptionHandler;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 请求参数校验切面
 *
 * @author SongYanBin
 * @copyright 2022-2099 SongYanBin All Rights Reserved.
 * @since 2022/11/29
 **/
@Aspect
@Component
public class ReqArgValidAspect {

    @Pointcut("@annotation(com.iduck.request.annotation.ReqArgValid)")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object reqArgValidAdvice(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        ReqArgValid annotation = method.getAnnotation(ReqArgValid.class);
        String[] execMethods = annotation.method();
        String errorMsg = annotation.errorMsg();
        for (Object arg : pjp.getArgs()) {
            Class<?> clazz = arg.getClass();
            for (String methodName : execMethods) {
                Method m = clazz.getMethod(methodName);
                m.setAccessible(true);

                // 指定校验方法返回值为boolean
                boolean result = (boolean) m.invoke(arg);
                if (!result) {
                    IExceptionHandler.pushValidExc(errorMsg);
                }
            }
        }
        return pjp.proceed();
    }

}
