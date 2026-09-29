package com.example.labfinal.dto;

import com.example.labfinal.model.CaseStatus;
import com.example.labfinal.model.Priority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CaseResponseDTO {

    private String id;
    private String caseId;
    private String title;
    private String leadDetective;
    private Priority priority;
    private CaseStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long version;
}
