package com.example.hellomomgotest.domain.course;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;

    public Course findByCode(String code ){
        return courseRepository.findByCode(code).orElse(null);
    }

    public Course save(Course course){
        return courseRepository.save(course);
    }
}
