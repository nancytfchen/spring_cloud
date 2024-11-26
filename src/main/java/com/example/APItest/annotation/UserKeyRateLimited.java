package com.example.APItest.annotation;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@RateLimiter(name = "userKeyLimiter")
public @interface UserKeyRateLimited {
}