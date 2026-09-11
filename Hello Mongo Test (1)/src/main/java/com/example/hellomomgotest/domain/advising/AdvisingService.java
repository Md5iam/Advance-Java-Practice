package com.example.hellomomgotest.domain.advising;

import com.example.hellomomgotest.domain.course.Course;
import com.example.hellomomgotest.domain.course.CourseService;
import com.example.hellomomgotest.domain.student.Student;
import com.example.hellomomgotest.domain.student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdvisingService {
    private final StudentService studentService;
    private final CourseService courseService;
    private final AdvisingRepository advisingRepository;

    public Advising save(AdvisingDTO dto){

        Student student = studentService.findById(dto.studentId());
        Course course = courseService.findByCode(dto.courseCode());

        Advising advising = Advising.builder()
                .semester("Spring 2026")
                .student(student)
                .course(course)
                .build();
        return advisingRepository.save(advising);
    }
}
