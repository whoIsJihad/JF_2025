package com.example.myapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.myapp.dto.AttractionDto;
import com.example.myapp.model.Attraction;
import com.example.myapp.service.AttractionService;

@RestController
@RequestMapping("/api/attractions")
public class AttractionController {
    private final AttractionService attractionService;
    @Autowired
    public AttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<AttractionDto>> getAllAttractions() {
        List<Attraction> attractions = attractionService.getAllAttractions();
        List<AttractionDto> response = new ArrayList<>();
        for (Attraction attraction : attractions) {
            AttractionDto dto = new AttractionDto();
            dto.setId(attraction.getId());
            dto.setName(attraction.getName());
            dto.setType(attraction.getType());
            dto.setAddress(attraction.getAddress());
            dto.setLocationCoords(attraction.getLocationCoords());
            response.add(dto);
        }
        return ResponseEntity.ok(response);
    }
}
