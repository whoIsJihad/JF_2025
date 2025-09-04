package com.example.myapp.dto;

import lombok.Getter;
import lombok.Setter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * DTO for transferring time slot data.
 * This is a simple data carrier with no business logic.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor  
public class TimeSlotDto {
    private LocalDateTime start;
    private LocalDateTime end;  
}