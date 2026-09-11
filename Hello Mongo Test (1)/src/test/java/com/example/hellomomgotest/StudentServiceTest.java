package com.example.hellomomgotest;

import com.example.hellomomgotest.domain.student.Student;
import com.example.hellomomgotest.domain.student.StudentSaveDTO;
import com.example.hellomomgotest.domain.student.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;

    @Test
    public void save(){
        StudentSaveDTO dto = new StudentSaveDTO("Mr. Java", 48, 3.52 , "approved");
        Student student =  studentService.save(dto);
        Assertions.assertEquals(dto.name(), student.getName());
    }

    @Test
    public void get(){
        Student student = studentService.findById("6aa3b77a4d112b4eb40880b3");
        Assertions.assertEquals("Mr. Java", student.getName());
    }

}
