//package com.example.APItest.config;
//
//import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
//import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import reactor.core.publisher.Mono;
//
//@Configuration
//public class RateLimitConfig {
//
//    @Bean
//    public KeyResolver userKeyResolver() {
//        return exchange -> Mono.just(
//                exchange.getRequest().getRemoteAddress().getAddress().getHostAddress() + "_" +
//                        exchange.getRequest().getPath().value());
//    }
//
////    @Bean
////    public RedisRateLimiter customRateLimiter() {
////        return new RedisRateLimiter(10,20);
////    }
//
//}