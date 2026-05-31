package com.carbontrack.activity_service.service;

import org.springframework.stereotype.Service;

@Service
public class CO2CalculationService {
    public double calculateCO2(String type, double value) {
        switch (type.toLowerCase()) {
            case "car": {
                return value * 0.12;
            }
            case "flight": {
                return value * 0.255;
            }
            case "train": {
                return value * 0.041;
            }
            case "electricity": {
                return value * 0.233;
            }
        }
        return 0.0;
    }
}
