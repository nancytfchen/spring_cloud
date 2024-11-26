package com.example.APItest.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestLimit {
    String key() default ""; //不同接口不同流量控制
    int value() default 10; //默认限制单位时间内的请求次数
    long timeout() default 60; //默认时间单位，单位为秒
    TimeUnit timeUnit() default TimeUnit.SECONDS;
}

