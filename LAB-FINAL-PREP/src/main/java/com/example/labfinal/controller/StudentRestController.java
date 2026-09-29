package com.example.labfinal.controller;

import com.example.labfinal.dto.StudentCreateDTO;
import com.example.labfinal.dto.StudentGpaRecord;
import com.example.labfinal.entity.Student;
import com.example.labfinal.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentRestController {

    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody StudentCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createStudent(dto));
    }

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<Student>> getStudentsPaged(Pageable pageable) {
        return ResponseEntity.ok(studentService.getStudentsPaged(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @GetMapping("/{id}/record")
    public ResponseEntity<StudentGpaRecord> getStudentRecord(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentRecord(id));
    }

    @GetMapping("/average-gpa")
    public ResponseEntity<Double> getAverageGpa() {
        return ResponseEntity.ok(studentService.getAverageGpa());
    }

    @GetMapping("/honor-students")
    public ResponseEntity<List<Student>> getHonorStudents(@RequestParam(name = "minGpa", defaultValue = "3.5") double minGpa) {
        return ResponseEntity.ok(studentService.getHonorStudents(minGpa));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
