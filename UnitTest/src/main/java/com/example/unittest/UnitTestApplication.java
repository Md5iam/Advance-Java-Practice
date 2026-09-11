package com.example.unittest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UnitTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(UnitTestApplication.class, args);

//        Student s1 = new Student(1, "Mr. Java", "java@gmail.com", true);

        Student s1 = Student.builder().id(2).name("Mr. PHP").build();
        Student s2 = s1.toBuilder().email("java@gmail.com").build();


    }

}
