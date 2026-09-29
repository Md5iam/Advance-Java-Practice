package com.example.labfinal.ai;

import com.example.labfinal.dto.AiPromptRequest;
import com.example.labfinal.dto.AiPromptResponse;
import com.example.labfinal.service.AiService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringAiServiceTest {

    @Autowired
    private AiService aiService;

    @Test
    void testSpringAiPromptExecution() {
        AiPromptRequest request = AiPromptRequest.builder()
                .prompt("What is Spring AI?")
                .build();

        AiPromptResponse response = aiService.processPrompt(request);

        Assertions.assertNotNull(response);
        Assertions.assertNotNull(response.getResponse());
        Assertions.assertFalse(response.getResponse().isBlank());
        Assertions.assertTrue(response.getResponse().contains("Spring AI"));
    }

    @Test
    void testSpringAiStudentAnalysisPrompt() {
        AiPromptRequest request = AiPromptRequest.builder()
                .prompt("Evaluate student academic performance with CGPA 3.85 in CSE")
                .build();

        AiPromptResponse response = aiService.processPrompt(request);

        Assertions.assertNotNull(response);
        Assertions.assertTrue(response.getResponse().contains("Student Analysis Report"));
    }
}
