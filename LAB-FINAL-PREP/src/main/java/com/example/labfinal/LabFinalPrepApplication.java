package com.example.labfinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories(basePackages = "com.example.labfinal.repository.jpa")
@EnableMongoRepositories(basePackages = "com.example.labfinal.repository.mongo")
public class LabFinalPrepApplication {

    public static void main(String[] args) {
        SpringApplication.run(LabFinalPrepApplication.class, args);
    }
}
