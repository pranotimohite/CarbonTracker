package com.carbontrack.carbontrack.controller;

import com.carbontrack.carbontrack.entity.GenAIResponse;
import com.carbontrack.carbontrack.entity.GenAiRequest;
import com.carbontrack.carbontrack.service.UnifiedAIAgentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = {"/api/bot"})
public class CarbonAIAgentController {

    @Autowired
    private final UnifiedAIAgentService aiAgentService;
    private static final Logger logger = LoggerFactory.getLogger(CarbonAIAgentController.class);

    @PostMapping("/chat")
    @Operation(summary = "AI Chat Agent", description = "Ask your question to this AI chat agent")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Response received from AI Agent"),
            @ApiResponse(responseCode = "401", description = "Unauthorized - invalid or missing token")
    })
    public ResponseEntity<GenAIResponse> chat(@Valid @RequestBody GenAiRequest request) {
        String prompt = request.getPrompt().trim();

        try {
            Optional<String> maybeResponse = Optional.ofNullable(aiAgentService.createChat(prompt));

            if (maybeResponse.isPresent()) {
                String aiResponse = maybeResponse.get();
                // If you want, pass model information into GenAIResponse.success(...)
                return ResponseEntity.ok(GenAIResponse.success(aiResponse, null));
            } else {
                // No response from AI; return a structured response (200 with ok=false)
                return ResponseEntity.ok(GenAIResponse.error("No response from AI"));
            }
        } catch (Exception ex) {
            logger.error("Failed to process chat request", ex);
            return ResponseEntity.status(500).body(GenAIResponse.error("Internal server error"));
        }
    }
}
