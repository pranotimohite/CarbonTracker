package com.carbontrack.carbontrack.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.client.OpenAIClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@EnableAsync
@Service
public class UnifiedAIAgentService {

    private final OpenAIClient openAIClient;
    private final ObjectMapper objectMapper; // ✓ Reusable instance
    private static final Logger logger = LoggerFactory.getLogger(UnifiedAIAgentService.class);


    public UnifiedAIAgentService(OpenAIClient openAIClient, ObjectMapper objectMapper) {
        this.openAIClient = openAIClient;
        this.objectMapper = objectMapper;
    }

    public String createChat(String prompt) {

        try {
            ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                    .addUserMessage(prompt)
                    .model(ChatModel.GPT_5_MINI) // or GPT_3_5_TURBO
                    .build();

            ChatCompletion completion = openAIClient.chat().completions().create(params);

            return completion.choices().getFirst().message().content()
                    .orElse(""); // ✓ Handle empty content safely
        } catch (IndexOutOfBoundsException e) {
            logger.error("No choices in completion", e); // ✓ Specific exception, proper logging
            throw new RuntimeException("Failed to get AI response", e);
        }
    }
}
