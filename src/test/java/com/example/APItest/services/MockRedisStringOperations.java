package com.example.APItest.services;

import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class MockRedisStringOperations extends StringRedisTemplate {
    private final ValueOperations<String, String> valueOps = new MockValueOperations();

    @Override
    public ValueOperations<String, String> opsForValue() {
        return valueOps;
    }

    private static class MockValueOperations implements ValueOperations<String, String> {
        private Map<String, String> store = new HashMap<>();

        @Override
        public String get(Object key) {
            return store.get(key);
        }

        @Override
        public String getAndDelete(String key) {
            return "";
        }

        @Override
        public String getAndExpire(String key, long timeout, TimeUnit unit) {
            return "";
        }

        @Override
        public String getAndExpire(String key, Duration timeout) {
            return "";
        }

        @Override
        public String getAndPersist(String key) {
            return "";
        }

        @Override
        public String getAndSet(String key, String value) {
            return "";
        }

        @Override
        public List<String> multiGet(Collection<String> keys) {
            return List.of();
        }

        @Override
        public Long increment(String key) {
            return 0L;
        }

        @Override
        public Long increment(String key, long delta) {
            return 0L;
        }

        @Override
        public Double increment(String key, double delta) {
            return 0.0;
        }

        @Override
        public Long decrement(String key) {
            return 0L;
        }

        @Override
        public Long decrement(String key, long delta) {
            return 0L;
        }

        @Override
        public Integer append(String key, String value) {
            return 0;
        }

        @Override
        public String get(String key, long start, long end) {
            return "";
        }

        @Override
        public void set(String key, String value, long offset) {

        }

        @Override
        public Long size(String key) {
            return 0L;
        }

        @Override
        public Boolean setBit(String key, long offset, boolean value) {
            return null;
        }

        @Override
        public Boolean getBit(String key, long offset) {
            return null;
        }

        @Override
        public List<Long> bitField(String key, BitFieldSubCommands subCommands) {
            return List.of();
        }

        @Override
        public RedisOperations<String, String> getOperations() {
            return null;
        }

        @Override
        public void set(String key, String value) {
            store.put(key, value);
        }

        @Override
        public void set(String key, String value, long timeout, TimeUnit unit) {

        }

        @Override
        public Boolean setIfAbsent(String key, String value) {
            return null;
        }

        @Override
        public Boolean setIfAbsent(String key, String value, long timeout, TimeUnit unit) {
            return null;
        }

        @Override
        public Boolean setIfPresent(String key, String value) {
            return null;
        }

        @Override
        public Boolean setIfPresent(String key, String value, long timeout, TimeUnit unit) {
            return null;
        }

        @Override
        public void multiSet(Map<? extends String, ? extends String> map) {

        }

        @Override
        public Boolean multiSetIfAbsent(Map<? extends String, ? extends String> map) {
            return null;
        }

        // 其他方法可以根据需要实现或者抛出未实现异常
    }
}