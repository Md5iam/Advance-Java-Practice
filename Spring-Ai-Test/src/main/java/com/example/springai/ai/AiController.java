package com.example.springai.ai;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AiController {
    private final AiService aiService;

    @GetMapping("/ask")
    public String ask(@RequestParam String message) {
        return aiService.ask(message);
    }
}
