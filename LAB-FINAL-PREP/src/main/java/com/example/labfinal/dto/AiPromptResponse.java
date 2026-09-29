package com.example.labfinal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiPromptResponse {

    private String prompt;
    private String response;
    private String model;
    private LocalDateTime generatedAt;
}
