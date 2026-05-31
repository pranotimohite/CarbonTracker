package com.carbontrack.carbontrack.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenAIMessage {

    private String role;
    private String content;

    public GenAIMessage(String role, String content) {
        this.role = role;
        this.content = content;
    }
}
