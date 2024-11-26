package com.example.APItest.config;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;
import org.springframework.cglib.core.internal.Function;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;

import java.time.Duration;

@Configuration
public class MyRateLimiterConfig {

    @Bean
    public RateLimiterRegistry rateLimiterRegistry() {
        // 使用RateLimiterConfigBuilder来构建配置实例
        RateLimiterConfig config = RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .limitForPeriod(10) // 根据需要调整
                .timeoutDuration(Duration.ofMillis(100)) // 如果在该时间段内没有权限则超时
                .build();

        return RateLimiterRegistry.of(config);
    }

    @Bean
    public Function<ServerWebExchange, String> userKeyResolver() {
        return exchange -> exchange.getRequest().getRemoteAddress().getAddress().getHostAddress() + "_" +
                        exchange.getRequest().getPath().value(); // 或者使用头部来定义自定义用户键
    }

    @Bean
    public RateLimiter userKeyRateLimiter(RateLimiterRegistry registry, Function<ServerWebExchange, String> userKeyResolver) {
        // 确保引用的是正确的配置对象，例如使用上面定义的配置或者直接构建一个新的配置
        RateLimiterConfig customConfig = RateLimiterConfig.custom()
                .limitRefreshPeriod(Duration.ofSeconds(1))
                .limitForPeriod(10)
                .timeoutDuration(Duration.ofMillis(100))
                .build();

        // 使用正确的配置对象创建RateLimiter实例
        return RateLimiter.of("userKeyLimiter", customConfig);
    }
}