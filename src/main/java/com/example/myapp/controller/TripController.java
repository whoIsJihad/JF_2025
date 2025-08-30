package com.example.myapp.controller;

import com.example.myapp.model.*;
import com.example.myapp.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")


public class TripController {
    private final TripService tripService;

    @Autowired
    public TripController(TripService tripService) {
        this.tripService = tripService;
    }

    @PostMapping("/createTrip")
    public ResponseEntity<Trip> createTrip(@RequestBody Trip trip, @RequestParam Long userId) {
        Trip createdTrip = tripService.createTrip(trip, userId);
        return new ResponseEntity<>(createdTrip, HttpStatus.CREATED);
    }

}