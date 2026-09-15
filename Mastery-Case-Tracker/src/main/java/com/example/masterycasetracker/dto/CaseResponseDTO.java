package com.example.masterycasetracker.dto;

import com.example.masterycasetracker.model.CaseStatus;
import com.example.masterycasetracker.model.Priority;
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
}
