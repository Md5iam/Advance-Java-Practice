package com.example.labfinal.dto;

public record DashboardStatsDTO(
        long totalStudents,
        double averageGpa,
        long totalDepartments,
        long totalCourses,
        long totalCases,
        long openCases,
        long inProgressCases,
        long resolvedCases
) {
}
