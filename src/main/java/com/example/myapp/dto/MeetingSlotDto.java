package com.example.myapp.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * A simple DTO to represent a single business meeting or busy period.
 * This is a nested part of the main TripCreationRequest "order form".
 */
@Getter
@Setter
public class MeetingSlotDto {

    private LocalDate meetingDate;
    private LocalTime startTime;
    private LocalTime endTime;

}