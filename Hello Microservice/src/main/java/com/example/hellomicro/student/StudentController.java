package com.example.hellomicro.student;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class StudentController {
    private final StudentService studentService;

    @GetMapping("all")
    public List<Student> getAll(){
        return studentService.getAll();
    }

    @GetMapping("{id}")
    public Student get(@PathVariable int id ){
        return studentService.get(id);
    }

    @PostMapping
    public void create(@RequestBody Student student){
        studentService.addStudent(student);
    }
}
