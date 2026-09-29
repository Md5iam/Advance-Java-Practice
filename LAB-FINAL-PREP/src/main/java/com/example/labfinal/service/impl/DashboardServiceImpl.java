package com.example.labfinal.service.impl;

import com.example.labfinal.dto.DashboardStatsDTO;
import com.example.labfinal.model.CaseStatus;
import com.example.labfinal.repository.jpa.CourseRepository;
import com.example.labfinal.repository.jpa.DepartmentRepository;
import com.example.labfinal.repository.jpa.StudentRepository;
import com.example.labfinal.repository.mongo.CaseRepository;
import com.example.labfinal.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final CourseRepository courseRepository;
    private final CaseRepository caseRepository;

    @Override
    public DashboardStatsDTO getDashboardStats() {
        long totalStudents = studentRepository.count();
        Double avgGpa = studentRepository.calculateAverageGpa();
        double averageGpa = avgGpa != null ? Math.round(avgGpa * 100.0) / 100.0 : 0.0;
        long totalDepts = departmentRepository.count();
        long totalCourses = courseRepository.count();

        long totalCases = caseRepository.count();
        long openCases = caseRepository.countByStatus(CaseStatus.OPEN);
        long inProgressCases = caseRepository.countByStatus(CaseStatus.IN_PROGRESS);
        long resolvedCases = caseRepository.countByStatus(CaseStatus.RESOLVED);

        return new DashboardStatsDTO(
                totalStudents,
                averageGpa,
                totalDepts,
                totalCourses,
                totalCases,
                openCases,
                inProgressCases,
                resolvedCases
        );
    }
}
