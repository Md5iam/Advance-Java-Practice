package com.example.labfinal.service.impl;

import com.example.labfinal.document.CaseRecord;
import com.example.labfinal.dto.AiPromptRequest;
import com.example.labfinal.dto.AiPromptResponse;
import com.example.labfinal.entity.Student;
import com.example.labfinal.exception.ResourceNotFoundException;
import com.example.labfinal.repository.jpa.StudentRepository;
import com.example.labfinal.repository.mongo.CaseRepository;
import com.example.labfinal.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiServiceImpl implements AiService {

    private final ChatModel chatModel;
    private final StudentRepository studentRepository;
    private final CaseRepository caseRepository;

    @Override
    public AiPromptResponse processPrompt(AiPromptRequest request) {
        Prompt prompt = new Prompt(request.getPrompt());
        ChatResponse chatResponse = chatModel.call(prompt);
        String reply = chatResponse.getResult().getOutput().getText();

        return AiPromptResponse.builder()
                .prompt(request.getPrompt())
                .response(reply)
                .model("Spring-AI-EducationalChatModel")
                .generatedAt(LocalDateTime.now())
                .build();
    }

    @Override
    public AiPromptResponse analyzeStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + studentId));

        String departmentName = student.getDepartment() != null ? student.getDepartment().getName() : "General";
        PromptTemplate promptTemplate = new PromptTemplate(
                "Analyze academic standing for student {name} (ID: {studentId}) enrolled in {department} with CGPA of {gpa}. Recommend academic improvement steps."
        );
        Map<String, Object> variables = Map.of(
                "name", student.getName(),
                "studentId", student.getStudentId(),
                "department", departmentName,
                "gpa", student.getGpa()
        );
        Prompt prompt = promptTemplate.create(variables);
        ChatResponse chatResponse = chatModel.call(prompt);
        String reply = chatResponse.getResult().getOutput().getText();

        return AiPromptResponse.builder()
                .prompt(prompt.getContents())
                .response(reply)
                .model("Spring-AI-EducationalChatModel")
                .generatedAt(LocalDateTime.now())
                .build();
    }

    @Override
    public AiPromptResponse recommendCase(String caseId) {
        CaseRecord caseRecord = caseRepository.findByCaseId(caseId)
                .or(() -> caseRepository.findById(caseId))
                .orElseThrow(() -> new ResourceNotFoundException("Case record not found with ID: " + caseId));

        String detective = caseRecord.getLeadDetective() != null ? caseRecord.getLeadDetective() : "Unassigned";
        PromptTemplate promptTemplate = new PromptTemplate(
                "Analyze case {caseId} titled '{title}' assigned to detective {detective} with priority {priority} and status {status}. Recommend immediate triage actions."
        );
        Map<String, Object> variables = Map.of(
                "caseId", caseRecord.getCaseId(),
                "title", caseRecord.getTitle(),
                "detective", detective,
                "priority", caseRecord.getPriority().name(),
                "status", caseRecord.getStatus().name()
        );
        Prompt prompt = promptTemplate.create(variables);
        ChatResponse chatResponse = chatModel.call(prompt);
        String reply = chatResponse.getResult().getOutput().getText();

        return AiPromptResponse.builder()
                .prompt(prompt.getContents())
                .response(reply)
                .model("Spring-AI-EducationalChatModel")
                .generatedAt(LocalDateTime.now())
                .build();
    }
}
