package com.example.APItest.controller;

import com.example.APItest.annotation.RequestLimit;
import com.example.APItest.services.MessageService;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1")
public class ApiController1 {
    Logger log = Logger.getLogger(ApiController1.class.getName());
    private final RateLimiter limiter = RateLimiter.create(100.0);
    public MessageService messageService;
    @Autowired
    public ApiController1(MessageService messageService) {
        this.messageService = messageService;
    }
    @GetMapping("/api1")
    @RequestLimit(key = "multiAPI",value = 1000, timeout = 1)
    public ResponseEntity<String> api1Endpoint(@RequestParam("key") String key, @RequestParam("message") String message) throws InterruptedException {
        boolean tryAcquire = limiter.tryAcquire(1, TimeUnit.SECONDS);
        if (!tryAcquire) {
            log.warning("当前排队人数太多， 请稍后再试！");
            ResponseEntity<String> response = new ResponseEntity<String>(HttpStatus.TOO_MANY_REQUESTS);
            return response;

        }
        //messageService.sendMessageToRedis(key, message);
        //Thread.sleep(10000);
        return ResponseEntity.ok("Welcome to API 1 is for Get request, your request was processed successfully.");
    }

}