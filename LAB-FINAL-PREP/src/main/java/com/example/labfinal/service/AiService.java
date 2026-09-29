package com.example.labfinal.service;

import com.example.labfinal.dto.AiPromptRequest;
import com.example.labfinal.dto.AiPromptResponse;

public interface AiService {

    AiPromptResponse processPrompt(AiPromptRequest request);

    AiPromptResponse analyzeStudent(Long studentId);

    AiPromptResponse recommendCase(String caseId);
}
