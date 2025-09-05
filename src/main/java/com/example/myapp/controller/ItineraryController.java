package com.example.myapp.controller;

import com.example.myapp.dto.*;
import com.example.myapp.service.ItineraryService;
import com.example.myapp.service.TimeSlot;
import com.example.myapp.model.Attraction;
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

    @GetMapping("/{tripId}/available-attractions")
    public ResponseEntity<List<AttractionDto>> getAvailableAttractions(@PathVariable Long tripId){
        List<AttractionDto> attractions=itineraryService.findAvailableAttractions(tripId);
        return ResponseEntity.ok(attractions);
    }

    @GetMapping("/{tripId}/attractions")
    public ResponseEntity<List<AttractionDto>> getAttractionsForTrip(@PathVariable Long tripId){
        List<Attraction> attractions=itineraryService.findAttractionsForTrip(tripId);
        List<AttractionDto> response=new ArrayList<>();
        for(Attraction attraction:attractions){
            AttractionDto dto=new AttractionDto();
            dto.setId(attraction.getId());
            dto.setName(attraction.getName());
            dto.setType(attraction.getType());
            dto.setAddress(attraction.getAddress());
            dto.setLocationCoords(attraction.getLocationCoords());
            dto.setDescription(attraction.getDescription());
            dto.setAvgRating(attraction.getAvgRating());
            dto.setCityName(attraction.getCity().getName());
            response.add(dto);
        }
        return ResponseEntity.ok(response);
    }

}