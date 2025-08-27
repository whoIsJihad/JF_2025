package com.example.myapp.model;

public class PlanRequest {
    private String city;
    private String preferences;

    public PlanRequest() {}  // default constructor needed by Spring

    public PlanRequest(String city, String preferences) {
        this.city = city;
        this.preferences = preferences;
    }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getPreferences() { return preferences; }
    public void setPreferences(String preferences) { this.preferences = preferences; }
}
