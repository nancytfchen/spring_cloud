package com.example.APItest.controller;
import com.example.APItest.annotation.RequestLimit;
import com.example.APItest.annotation.UserKeyRateLimited;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ApiController2 {

    @PostMapping("/api2")
    @RequestLimit(key = "multiAPI",value = 1000, timeout = 60)
    public ResponseEntity<String> api2Endpoint(@RequestParam("key") String key, @RequestParam("message") String message) throws InterruptedException {
        //Thread.sleep(10000);
        return ResponseEntity.ok("API 2 is for Post request! Your access is controlled by user-specific rate limits.");
    }
}