package com.example.labfinal.service;

import com.example.labfinal.dto.StudentCreateDTO;
import com.example.labfinal.dto.StudentGpaRecord;
import com.example.labfinal.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StudentService {
    Student createStudent(StudentCreateDTO dto);
    List<Student> getAllStudents();
    Page<Student> getStudentsPaged(Pageable pageable);
    Student getStudentById(Long id);
    void deleteStudent(Long id);
    StudentGpaRecord getStudentRecord(Long id);
    Double getAverageGpa();
    List<Student> getHonorStudents(double minGpa);
}
