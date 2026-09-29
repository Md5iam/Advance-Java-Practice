package com.example.labfinal.service.impl;

import com.example.labfinal.entity.Course;
import com.example.labfinal.entity.Department;
import com.example.labfinal.repository.jpa.CourseRepository;
import com.example.labfinal.repository.jpa.DepartmentRepository;
import com.example.labfinal.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final CourseRepository courseRepository;

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }
}
