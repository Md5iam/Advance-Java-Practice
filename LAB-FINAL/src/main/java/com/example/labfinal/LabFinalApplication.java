package com.example.labfinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LabFinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabFinalApplication.class, args);
    }

}
