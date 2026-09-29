package com.example.labfinal.controller;

import com.example.labfinal.dto.AiPromptRequest;
import com.example.labfinal.dto.AiPromptResponse;
import com.example.labfinal.service.AiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiRestController {

    private final AiService aiService;

    @PostMapping("/chat")
    public ResponseEntity<AiPromptResponse> chat(@Valid @RequestBody AiPromptRequest request) {
        return ResponseEntity.ok(aiService.processPrompt(request));
    }

    @PostMapping("/students/{id}/analyze")
    public ResponseEntity<AiPromptResponse> analyzeStudent(@PathVariable Long id) {
        return ResponseEntity.ok(aiService.analyzeStudent(id));
    }

    @PostMapping("/cases/{caseId}/recommend")
    public ResponseEntity<AiPromptResponse> recommendCase(@PathVariable String caseId) {
        return ResponseEntity.ok(aiService.recommendCase(caseId));
    }
}
