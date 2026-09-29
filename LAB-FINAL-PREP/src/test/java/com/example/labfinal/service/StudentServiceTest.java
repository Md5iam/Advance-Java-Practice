package com.example.labfinal.service;

import com.example.labfinal.entity.Student;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    void testGetAllStudents() {
        List<Student> students = studentService.getAllStudents();
        Assertions.assertNotNull(students);
    }

    @Test
    void testGetAverageGpa() {
        Double avgGpa = studentService.getAverageGpa();
        if (avgGpa != null) {
            Assertions.assertTrue(avgGpa >= 0.0 && avgGpa <= 4.0);
        }
    }
}
