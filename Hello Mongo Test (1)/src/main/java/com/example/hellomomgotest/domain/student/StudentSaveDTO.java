package com.example.hellomomgotest.domain.student;

public record StudentSaveDTO(
        String name,
        int age,
        double cgpa,
        String status
) {
}
