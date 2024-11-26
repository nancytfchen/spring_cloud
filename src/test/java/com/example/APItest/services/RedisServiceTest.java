package com.example.APItest.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;


import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RedisServiceTest {

    @Autowired
    private MessageService redisService; // 假设有这样一个服务使用StringRedisTemplate

    @Test
    public void testRedis() {
        RedisTemplate<String, String> mockTemplate = (RedisTemplate<String, String>)Mockito.mock(RedisTemplate.class);
// 假设你已经有了 RedisTemplate 的 mock 对象 mockTemplate
// Mockito.mock(RedisTemplate.class);

// 创建一个 ValueOperations 的 mock 对象
        ValueOperations<String, String> opsForValueMock = Mockito.mock(ValueOperations.class);

// 配置 RedisTemplate 的 opsForValue() 方法返回刚才创建的 mock 对象
        Mockito.when(mockTemplate.opsForValue()).thenReturn(opsForValueMock);

// 现在可以成功设置 get 方法的模拟行为了
        Mockito.when(opsForValueMock.get("key")).thenReturn("value");
        //Mockito.when(mockTemplate.opsForValue().get("key")).thenReturn("value");

        // 使用模拟的RedisTemplate进行测试
        redisService.setRedisTemplate(mockTemplate);
        assertEquals("value", redisService.getValueFromRedis("key"));
    }
}