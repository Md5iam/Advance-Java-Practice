package com.example.unittest;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MathServiceTest {

    @Autowired
    MathService mathService;

    @BeforeAll
    public static void beforeAllTest(){
        System.out.println("Called before all each test");
    }

    @BeforeEach
    public void before(){
        System.out.println("Called before each test");
    }

    @AfterEach
    public void after(){
        System.out.println("Called after each test");
    }

    @AfterAll
    public static void afterAllTest(){
        System.out.println("Called after  all each test");
    }

    @Test
    void addTest(){
        int res = mathService.add(1,2);
//        System.out.println("result " + res);
        Assertions.assertEquals(3, res);
    }


    @Test
    void sub(){
//        System.out.println("Hello world from sub method");
        int res = mathService.sub(5, 1 );
        Assertions.assertEquals(4, res);
    }



}
