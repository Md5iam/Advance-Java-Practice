package com.example.unittest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class MathService {
    int add(int a, int b){
        return a + b ;
    }
     int sub(int a , int b){
        return a - b ;
    }
     int mul(int a , int b){
        return a * b ;
    }
     int div(int a , int b){
        return a / b ;
    }
}
