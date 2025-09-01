package com.example.myapp.controller;

import com.example.myapp.dto.TripCreationRequest;
import com.example.myapp.dto.TripResponse;
import com.example.myapp.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping
    public ResponseEntity<TripResponse> createTrip(@RequestBody TripCreationRequest request) {
        TripResponse createdTrip = tripService.createTrip(request);
        return new ResponseEntity<>(createdTrip, HttpStatus.CREATED);
    }
}
