package com.example.hellomicro.student;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    private static List<Student> studentList = new ArrayList<>();

    public void addStudent(Student student){
        studentList.add(student);
    }

    public List<Student> getAll(){
        return studentList;
    }

    public Student get(int id ){
        return studentList.stream().filter(s-> s.getId() == id ).findFirst().orElse(null);
    }
}
