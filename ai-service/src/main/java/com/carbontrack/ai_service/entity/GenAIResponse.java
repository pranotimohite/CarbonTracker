package com.carbontrack.carbontrack.entity;

import java.io.Serializable;

/**
 * Simple DTO returned by the AI chat endpoint.
 */
public class GenAIResponse implements Serializable {
    private boolean ok;
    private String response;
    private String message; // optional error or info message
    private String model;   // optional model info
    private Long timestamp; // epoch millis

    public GenAIResponse() { }

    public GenAIResponse(boolean ok, String response, String message, String model, Long timestamp) {
        this.ok = ok;
        this.response = response;
        this.message = message;
        this.model = model;
        this.timestamp = timestamp;
    }

    public static GenAIResponse success(String response, String model) {
        return new GenAIResponse(true, response, null, model, System.currentTimeMillis());
    }

    public static GenAIResponse error(String message) {
        return new GenAIResponse(false, null, message, null, System.currentTimeMillis());
    }

    // getters / setters
    public boolean isOk() { return ok; }
    public void setOk(boolean ok) { this.ok = ok; }

    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }
}