package com.example.myapp.model;

public class PlanResponse {
    private String message;

    public PlanResponse() {}

    public PlanResponse(String message) {
        this.message = message;
    }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
