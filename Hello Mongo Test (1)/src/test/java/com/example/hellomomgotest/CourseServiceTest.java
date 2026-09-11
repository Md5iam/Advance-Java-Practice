package com.example.hellomomgotest;

import com.example.hellomomgotest.domain.course.Course;
import com.example.hellomomgotest.domain.course.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CourseServiceTest {
    @Autowired
    private CourseService courseService;

    @Test
    public void save(){
        Course course = new Course("CSE101", "Basic C", 3.0);
    }
}
