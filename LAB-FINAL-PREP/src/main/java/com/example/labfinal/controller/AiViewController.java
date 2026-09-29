package com.example.labfinal.controller;

import com.example.labfinal.dto.AiPromptRequest;
import com.example.labfinal.dto.AiPromptResponse;
import com.example.labfinal.service.AiService;
import com.example.labfinal.service.CaseService;
import com.example.labfinal.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AiViewController {

    private final AiService aiService;
    private final StudentService studentService;
    private final CaseService caseService;

    @GetMapping
    public String aiPage(Model model, Authentication authentication) {
        populateModel(model, authentication);
        return "ai";
    }

    @PostMapping("/chat")
    public String chat(@RequestParam("prompt") String prompt, Model model, Authentication authentication) {
        populateModel(model, authentication);
        AiPromptRequest request = AiPromptRequest.builder().prompt(prompt).build();
        AiPromptResponse response = aiService.processPrompt(request);
        model.addAttribute("aiResponse", response);
        model.addAttribute("lastPrompt", prompt);
        return "ai";
    }

    @PostMapping("/student")
    public String analyzeStudent(@RequestParam("studentId") Long studentId, Model model, Authentication authentication) {
        populateModel(model, authentication);
        AiPromptResponse response = aiService.analyzeStudent(studentId);
        model.addAttribute("aiResponse", response);
        model.addAttribute("selectedStudentId", studentId);
        return "ai";
    }

    @PostMapping("/case")
    public String recommendCase(@RequestParam("caseId") String caseId, Model model, Authentication authentication) {
        populateModel(model, authentication);
        AiPromptResponse response = aiService.recommendCase(caseId);
        model.addAttribute("aiResponse", response);
        model.addAttribute("selectedCaseId", caseId);
        return "ai";
    }

    private void populateModel(Model model, Authentication authentication) {
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("cases", caseService.getAllCases());
        model.addAttribute("username", authentication != null ? authentication.getName() : "User");
    }
}
