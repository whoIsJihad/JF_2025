package com.example.myapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * DTO for the "Create a Trip" API request.
 * This is the main "order form" that gathers all necessary user input.
 */
@Getter
@Setter
public class TripCreationRequest {

    private Long userId;

    private String cityName;

    private LocalDate startDate;
    private LocalDate endDate;

    private String hotelLocation; // e.g., "23.8103,90.4125"

    // A trip can have multiple busy periods
    private List<MeetingSlotDto> meetingSlots;

    // A trip can have multiple preferences
    private Set<String> preferenceNames; // e.g., ["Food", "Museum", "Sightseeing"]
}
