package com.carbontrack.carbontrack.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Choice {

    private int index;
    private GenAIMessage message;
    private String finish_reason;

}
