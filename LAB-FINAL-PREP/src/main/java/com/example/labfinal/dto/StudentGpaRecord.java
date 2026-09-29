package com.example.labfinal.dto;

public record StudentGpaRecord(
        String studentId,
        String name,
        double gpa
) {
    public StudentGpaRecord {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Student name cannot be empty");
        }
        name = name.trim().toUpperCase();
        if (gpa > 4.0) {
            gpa = 4.0;
        } else if (gpa < 0.0) {
            gpa = 0.0;
        }
    }
}
