package com.iduck.redis.aspect;

import cn.hutool.core.util.ObjUtil;
import com.iduck.redis.annotation.IRedisCache;
import com.iduck.redis.util.RedisHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * RedisCache切面
 *
 * @author SongYanBin
 * @since 2022/11/29
 **/
@Aspect
@Component
public class RedisCacheAspect {

    @Pointcut("@annotation(com.iduck.redis.annotation.IRedisCache)")
    public void pointCut() {

    }

    @Around("pointCut()")
    public Object redisCacheAdvice(ProceedingJoinPoint pjp) throws Throwable {
        Object result = null;
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        IRedisCache annotation = signature.getMethod().getAnnotation(IRedisCache.class);
        String key = annotation.key();
        IRedisCache.ObtainType obtain = annotation.obtain();
        long expire = annotation.expire();

        // TODO 后期优化,暂时这么写
        if (obtain == IRedisCache.ObtainType.ONLY_REDIS) {
            // 仅获取redis中数据,哪怕为null,直接返回结果
            result = RedisHelper.get(key);
        } else if (obtain == IRedisCache.ObtainType.REFRESH) {
            // 执行方法获取数据,并将数据存入redis（保持redis为最新数据,可供其他地方调用）
            result = pjp.proceed();
            this.setRedis(key, result, expire);
        } else if (obtain == IRedisCache.ObtainType.ABSENT) {
            // 先查找redis中数据,若redis没有再执行方法,返回数据（无法保证redis为最新数据）
            Object obj = RedisHelper.get(key);
            if (ObjUtil.isNull(obj)) {
                result = pjp.proceed();
                this.setRedis(key, result, expire);
            }
        }
        return result;
    }

    public void setRedis(String key, Object obj, long expireTime) {
        if (expireTime > 0) {
            RedisHelper.set(key, obj, expireTime, TimeUnit.SECONDS);
        } else {
            RedisHelper.set(key, obj);
        }
    }
}
