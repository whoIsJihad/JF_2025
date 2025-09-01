package com.example.myapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

/**
 * DTO for the API response after a trip is created.
 * This is the "receipt" we send back to the user.
 */
@Getter
@Setter
public class TripResponse {

    private Long id;
    private String cityName;
    private String country;
    private LocalDate startDate;
    private LocalDate endDate;
    private String hotelLocation;
    private UserResponse user; // Nested DTO to avoid exposing sensitive user data
    private Set<String> preferences;
}
