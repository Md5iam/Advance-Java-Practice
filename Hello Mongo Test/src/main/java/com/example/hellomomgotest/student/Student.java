package com.example.hellomomgotest.student;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    private String id ;

    private String name;
    private int age;
    private double cgpa;
    private String status;
}
