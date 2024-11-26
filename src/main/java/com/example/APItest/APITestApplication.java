package com.example.APItest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class APITestApplication {

	public static void main(String[] args) {
		SpringApplication.run(APITestApplication.class, args);
	}

}
