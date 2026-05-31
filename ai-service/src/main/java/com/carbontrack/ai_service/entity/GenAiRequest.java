package com.carbontrack.carbontrack.entity;

import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * Request DTO for AI chat.
 */
public class GenAiRequest implements Serializable {

    @NotBlank(message = "Prompt is required")
    private String prompt;

    public GenAiRequest() {}

    public GenAiRequest(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}