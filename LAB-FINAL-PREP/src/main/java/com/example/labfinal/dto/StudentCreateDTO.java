package com.example.labfinal.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentCreateDTO {

    @NotBlank(message = "Student ID is required")
    private String studentId;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "GPA is required")
    @DecimalMin(value = "0.0", message = "GPA cannot be less than 0.0")
    @DecimalMax(value = "4.0", message = "GPA cannot exceed 4.0")
    private Double gpa;

    private String streetAddress;
    private String city;
    private String state;
    private String country;

    private String mobileNumber;

    private String guardianName;
    private String guardianEmail;
    private String guardianMobile;

    @NotNull(message = "Department ID is required")
    private Long departmentId;
}
