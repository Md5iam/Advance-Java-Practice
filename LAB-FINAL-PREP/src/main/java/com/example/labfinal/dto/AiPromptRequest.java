package com.example.labfinal.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AiPromptRequest {

    @NotBlank(message = "Prompt cannot be blank")
    private String prompt;

    private String systemInstruction;
}
