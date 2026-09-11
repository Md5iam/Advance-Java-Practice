package com.example.hellomomgotest.domain.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Course {
    @Id
    private String id;

    private String code;
    private String title;
    private double credits;

    public Course(String code, String title, double credits) {
        this.code = code;
        this.title = title;
        this.credits = credits;
    }
}
