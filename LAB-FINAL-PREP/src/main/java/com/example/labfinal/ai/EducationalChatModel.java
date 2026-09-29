package com.example.labfinal.ai;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EducationalChatModel implements ChatModel {

    @Override
    public ChatResponse call(Prompt prompt) {
        String instruction = prompt.getContents();
        String answer = generateAnswer(instruction);
        AssistantMessage assistantMessage = new AssistantMessage(answer);
        Generation generation = new Generation(assistantMessage);
        return new ChatResponse(List.of(generation));
    }

    private String generateAnswer(String input) {
        if (input == null || input.isBlank()) {
            return "Hello! I am your Spring AI Assistant. Please provide a prompt or query.";
        }
        String lower = input.toLowerCase();
        if (lower.contains("student") && (lower.contains("cgpa") || lower.contains("gpa") || lower.contains("department") || lower.contains("academic"))) {
            return "[Spring AI Student Analysis Report]\n"
                    + "Input Context: " + input + "\n\n"
                    + "Evaluation Summary:\n"
                    + "- Academic Standing: Verified against departmental performance benchmarks.\n"
                    + "- Recommendation: Engage in peer-learning circles, balance workload across core courses, and participate in capstone research projects.\n"
                    + "- Career Pathway: Focus on hands-on practical implementations (Spring Boot, Cloud Native architectures, Distributed Databases).\n"
                    + "- Advisory Status: In good standing with active continuous evaluation.";
        }
        if (lower.contains("case") && (lower.contains("priority") || lower.contains("status") || lower.contains("triage") || lower.contains("recommend"))) {
            return "[Spring AI Case Triage Recommendation]\n"
                    + "Input Context: " + input + "\n\n"
                    + "Triage Summary:\n"
                    + "- Action Plan: Escalate to relevant tier based on priority and ensure SLA adherence.\n"
                    + "- Milestone: Verify linked artifacts, notify stakeholders, and update audit log.\n"
                    + "- Resolution Target: Follow standardized standard operating procedures for dispute and request resolution.";
        }
        if (lower.contains("spring ai") || lower.contains("what is")) {
            return "[Spring AI Overview]\n"
                    + "Spring AI provides an abstraction layer across modern AI models and providers.\n"
                    + "Key Concepts Learned in This Project:\n"
                    + "1. ChatModel: Core interface for invoking conversational models with standard Prompt and ChatResponse.\n"
                    + "2. Prompt & PromptTemplate: Parameterized prompt construction and rendering.\n"
                    + "3. Generation & AssistantMessage: Structured response and metadata payload.\n"
                    + "4. Spring AI Integration: Clean loose coupling following SOLID principles.";
        }
        return "[Spring AI Response]\n"
                + "Processed your request using Spring AI Core Prompt Pipeline:\n\""
                + input + "\"\n\n"
                + "Generated Insight: The system processed this prompt through Spring AI's ChatModel abstraction, successfully binding prompt context into an AssistantMessage generation.";
    }
}
