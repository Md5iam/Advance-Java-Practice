package com.example.labfinal.config;

import com.example.labfinal.ai.EducationalChatModel;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class AiConfig {

    @Bean
    @Primary
    public ChatModel chatModel(EducationalChatModel educationalChatModel) {
        return educationalChatModel;
    }
}
