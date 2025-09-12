package com.example.myapp.dto;

import lombok.Data;

@Data
public class ItineraryItemDto {
    private Long id;
    private Long tripId;
    private Long attractionId;
    private String attractionName;
    private String attractionType;
    private String attractionAddress;
    private String attractionLocationCoords;
    private String visitDate; // ISO format
    private String startTime; // ISO format
    private String endTime; // ISO format
    private String status; // "SUGGESTED", "CONFIRMED"
}