package com.example.hellomicro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class HelloMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloMicroserviceApplication.class, args);
    }

}
