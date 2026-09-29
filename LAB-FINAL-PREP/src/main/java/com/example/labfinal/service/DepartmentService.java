package com.example.labfinal.service;

import com.example.labfinal.entity.Course;
import com.example.labfinal.entity.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> getAllDepartments();
    List<Course> getAllCourses();
}
