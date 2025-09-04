package com.example.myapp.controller;

import com.example.myapp.dto.TimeSlotDto;
import com.example.myapp.service.ItineraryService;
import com.example.myapp.service.TimeSlot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;



@RestController
@RequestMapping("/api/itinerary")
public class ItineraryController{

    private final ItineraryService itineraryService;
    @Autowired
    public ItineraryController(ItineraryService itineraryService) {
        this.itineraryService = itineraryService;   
    }

    @GetMapping("/{tripId}/available-slots")
    public ResponseEntity<List<TimeSlotDto>> getAvailableTimeSlots(@PathVariable Long tripId) {
        List<TimeSlot> availableSlots = itineraryService.getAvailableTimeSlots(tripId);

        List<TimeSlotDto> response=new ArrayList<>();
        for(TimeSlot slot:availableSlots){
            response.add(new TimeSlotDto(slot.getStart(),slot.getEnd()));
        }
        return ResponseEntity.ok(response);
    }


}