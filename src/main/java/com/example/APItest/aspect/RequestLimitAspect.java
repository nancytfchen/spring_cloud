package com.example.APItest.aspect;

import com.example.APItest.annotation.RequestLimit;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

@Aspect
@Component
public class RequestLimitAspect  {

    private Map<String, RateLimiter> rateLimiterMap = Maps.newConcurrentMap();

    @Around("@annotation(com.example.APItest.annotation.RequestLimit)")
    public Object around(ProceedingJoinPoint joinPoint ) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RequestLimit limiter = method.getAnnotation(RequestLimit.class);
        if(limiter != null){
            String key = limiter.key();
            RateLimiter rateLimiter= rateLimiterMap.computeIfAbsent(key, k -> {
                return RateLimiter.create(limiter.value());
            });
            boolean acquire = rateLimiter.tryAcquire(limiter.timeout(), limiter.timeUnit());
            if(acquire){
                return joinPoint.proceed();
            }else{
                throw new RuntimeException("too many request");
            }
        }
        return joinPoint.proceed();
    }
}
