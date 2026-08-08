package org.example.DTO;

public record StudentGpaRecord(
        String name,
        double gpa
) {
    public StudentGpaRecord{
        name = name.toUpperCase();
        gpa = 4.0;
    }
}
