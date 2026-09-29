package com.example.labfinal.controller;

import com.example.labfinal.dto.StudentCreateDTO;
import com.example.labfinal.entity.Student;
import com.example.labfinal.service.DepartmentService;
import com.example.labfinal.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentViewController {

    private final StudentService studentService;
    private final DepartmentService departmentService;

    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("departments", departmentService.getAllDepartments());
        model.addAttribute("courses", departmentService.getAllCourses());
        if (!model.containsAttribute("studentForm")) {
            model.addAttribute("studentForm", new StudentCreateDTO());
        }
        return "students";
    }

    @PostMapping("/add")
    public String addStudent(
            @Valid @ModelAttribute("studentForm") StudentCreateDTO dto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("students", studentService.getAllStudents());
            model.addAttribute("departments", departmentService.getAllDepartments());
            model.addAttribute("courses", departmentService.getAllCourses());
            return "students";
        }
        try {
            studentService.createStudent(dto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("students", studentService.getAllStudents());
            model.addAttribute("departments", departmentService.getAllDepartments());
            model.addAttribute("courses", departmentService.getAllCourses());
            return "students";
        }
        return "redirect:/students?success=true";
    }

    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students?deleted=true";
    }
}
