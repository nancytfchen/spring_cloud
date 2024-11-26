package com.example.APItest.controller;

import com.example.APItest.services.MessageService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class MultipleAPIFlowControlTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    public MessageService messageService;

    @BeforeEach
    void autoConfig() {
        RedisTemplate<String, String> mockTemplate = (RedisTemplate<String, String>) Mockito.mock(RedisTemplate.class);
        ValueOperations<String, String> opsForValueMock = Mockito.mock(ValueOperations.class);

        Mockito.when(mockTemplate.opsForValue()).thenReturn(opsForValueMock);
        Mockito.when(opsForValueMock.get("key")).thenReturn("value");
        this.messageService.setRedisTemplate(mockTemplate);
    }

    @Test
    void testMutipleAPIExceedingRateLimit() throws Exception {
        int totalRequests = 10000; // 总请求数
        int concurrencyLevel = 10000; // 并发级别
        AtomicInteger successCount1 = new AtomicInteger(0);
        AtomicInteger failureDueToRateLimit1 = new AtomicInteger(0);
        AtomicInteger successCount2 = new AtomicInteger(0);
        AtomicInteger failureDueToRateLimit2 = new AtomicInteger(0);
        AtomicInteger successCount3 = new AtomicInteger(0);
        AtomicInteger failureDueToRateLimit3 = new AtomicInteger(0);
        AtomicInteger ExceptionCount = new AtomicInteger(0);
        ExecutorService executorService = Executors.newFixedThreadPool(concurrencyLevel);

        for (int i = 0; i < totalRequests; i++) {
            final int requestNumber = i;
            executorService.submit(() -> {
                //System.out.println("requestNumber : " + requestNumber );
                try {
                    HttpStatus responseStatus1 = callAPI1();
                    HttpStatus responseStatus2 = callAPI2();
                    HttpStatus responseStatus3 = callAPI3();

                    if (responseStatus1.is2xxSuccessful()) {
                        successCount1.incrementAndGet();
                    } else if (responseStatus1 == HttpStatus.TOO_MANY_REQUESTS) { // 假设超过速率限制返回429状态码
                        failureDueToRateLimit1.incrementAndGet();
                    } else {
                        // 其他类型的失败，可根据实际情况处理
                        System.out.println("Request API1 " + requestNumber + " failed: " + responseStatus1);
                    }

                    if (responseStatus2.is2xxSuccessful()) {
                        successCount2.incrementAndGet();
                    } else if (responseStatus2 == HttpStatus.TOO_MANY_REQUESTS) { // 假设超过速率限制返回429状态码
                        failureDueToRateLimit2.incrementAndGet();
                    } else {
                        // 其他类型的失败，可根据实际情况处理
                        System.out.println("Request API1 " + requestNumber + " failed: " + responseStatus1);
                    }

                    if (responseStatus3.is2xxSuccessful()) {
                        successCount3.incrementAndGet();
                    } else if (responseStatus3 == HttpStatus.TOO_MANY_REQUESTS) { // 假设超过速率限制返回429状态码
                        failureDueToRateLimit3.incrementAndGet();
                    } else {
                        // 其他类型的失败，可根据实际情况处理
                        System.out.println("Request API1 " + requestNumber + " failed: " + responseStatus1);
                    }
                } catch (Exception e) {
                    ExceptionCount.incrementAndGet();
                    // 记录或处理异常
                    System.err.println("Request " + requestNumber + " failed: " + e.getMessage());
                }
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS); // 等待所有任务完成或超时

        System.out.println("successCount : API1 - " + successCount1 + " ; API2 - " + successCount2 + " ; API3 - " + successCount3);
        System.out.println("failureDueToRateLimit : API1 - " + failureDueToRateLimit1 + " ; API2 - " + failureDueToRateLimit2+ " ; API3 - " + failureDueToRateLimit3);
        System.out.println("ExceptionCount : " + ExceptionCount);
        // 验证至少有一些请求因速率限制而失败
        assertTrue(failureDueToRateLimit1.get() + failureDueToRateLimit2.get()+failureDueToRateLimit3.get() > 0, "No requests failed due to rate limiting.");

        // 可选：检查成功和失败的具体数量，根据实际情况调整期望值
        // assertEquals(expectedSuccessCount, successCount.get(), "Unexpected number of successful requests.");
    }

    HttpStatus callAPI1() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/api1")
                        .param("key", "key")
                        .param("message", "messages to Redis")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        return HttpStatus.valueOf(result.getResponse().getStatus());
    }

    HttpStatus callAPI2() throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/api2")
                        .param("key", "key")
                        .param("message", "messages to Redis")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        return HttpStatus.valueOf(result.getResponse().getStatus());
    }

    HttpStatus callAPI3() throws Exception {
        MvcResult result = mockMvc.perform(put("/api/v1/api3")
                        .param("key", "key")
                        .param("message", "messages to Redis")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();

        return HttpStatus.valueOf(result.getResponse().getStatus());
    }
}

