package com.carbontrack.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityDto {

    private Long id;
    private String type;
    private double value;
    private String unit;
    private double co2Emitted;
    private LocalDateTime timestamp;

}
