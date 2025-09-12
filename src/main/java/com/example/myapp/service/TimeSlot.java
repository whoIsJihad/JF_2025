package com.example.myapp.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * A simple helper class (a record would also work in modern Java) to represent
 * a continuous block of time with a start and an end.
 * This is used as a common data structure for both busy and free time slots
 * within the itinerary generation algorithm.
 */
@Data
@AllArgsConstructor
public class TimeSlot {
    private final LocalDateTime start;
    private final LocalDateTime end;
}
