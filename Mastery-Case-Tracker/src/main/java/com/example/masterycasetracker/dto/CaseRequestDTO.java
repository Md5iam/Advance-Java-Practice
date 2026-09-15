package com.example.masterycasetracker.dto;

import com.example.masterycasetracker.model.CaseStatus;
import com.example.masterycasetracker.model.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaseRequestDTO {

    @NotBlank(message = "Case ID is required")
    @Pattern(regexp = "^CASE-\\d{3,}$", message = "Case ID must follow format CASE-XXX (e.g. CASE-101)")
    private String caseId;

    @NotBlank(message = "Case Title is required")
    @Size(min = 3, max = 150, message = "Case Title must be between 3 and 150 characters")
    private String title;

    @NotBlank(message = "Lead Detective is required")
    @Size(min = 2, max = 100, message = "Lead Detective name must be between 2 and 100 characters")
    private String leadDetective;

    @NotNull(message = "Priority is required (HIGH, MEDIUM, LOW)")
    private Priority priority;

    @NotNull(message = "Status is required (OPEN, IN_PROGRESS, CLOSED)")
    private CaseStatus status;
}
