package com.example.hellomomgotest.domain.advising;

import com.example.hellomomgotest.domain.course.Course;
import com.example.hellomomgotest.domain.student.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Advising {
    @Id
    private String id;

    private String semester;
    private Student student;
    private Course course;
}
