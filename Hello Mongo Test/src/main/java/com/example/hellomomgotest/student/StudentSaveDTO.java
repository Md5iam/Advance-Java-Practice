package com.example.hellomomgotest.student;

public record StudentSaveDTO(
        String name,
        int age,
        double cgpa,
        String status
) {
}
