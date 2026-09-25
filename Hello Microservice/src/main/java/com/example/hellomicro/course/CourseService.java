package com.example.hellomicro.course;

import com.example.hellomicro.student.Student;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    private static List<Course> courseList = new ArrayList<>();

    public void addStudent(Course course){
        courseList.add(course);
    }

    public List<Course> getAll(){
        return courseList;
    }

    public Course get(String code ){
        return courseList.stream().filter(s-> s.getCode().equals(code) ).findFirst().orElse(null);
    }
}
