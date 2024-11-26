package com.example.APItest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    public MessageService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void sendMessageToRedis(String key, String message) {
        redisTemplate.opsForValue().set(key, message);
    }

    public void setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String getValueFromRedis(String key){
        return this.redisTemplate.opsForValue().get(key);
    }
}