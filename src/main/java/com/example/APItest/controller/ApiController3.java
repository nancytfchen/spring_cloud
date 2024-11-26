package com.example.APItest.controller;

import com.example.APItest.annotation.RequestLimit;
import com.example.APItest.annotation.UserKeyRateLimited;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ApiController3 {

    @PutMapping("/api3")
    @RequestLimit(key = "multiAPI",value = 1000, timeout = 60)
    public ResponseEntity<String> api3Endpoint(@RequestParam("key") String key, @RequestParam("message") String message) throws InterruptedException {
        //Thread.sleep(1000);
        return ResponseEntity.ok("Welcome to API 3 is for put request, your request was processed successfully.");
    }
}