package com.example.labfinal.dto;

import com.example.labfinal.model.CaseStatus;
import com.example.labfinal.model.Priority;
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
    @Pattern(regexp = "^CASE-\\d{3,}$", message = "Case ID must follow format CASE-XXX")
    private String caseId;

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 150, message = "Title must be between 3 and 150 characters")
    private String title;

    @NotBlank(message = "Lead detective is required")
    @Size(min = 2, max = 100, message = "Lead detective name must be between 2 and 100 characters")
    private String leadDetective;

    @NotNull(message = "Priority is required")
    private Priority priority;

    @NotNull(message = "Status is required")
    private CaseStatus status;
}
