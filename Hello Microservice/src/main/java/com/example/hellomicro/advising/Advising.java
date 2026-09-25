package com.example.hellomicro.advising;

import com.example.hellomicro.course.Course;
import com.example.hellomicro.student.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Advising {
    private String semester;
    private Student student;
    private Course course;
}
