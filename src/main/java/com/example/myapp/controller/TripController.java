package com.example.myapp.controller;
import java.util.*;
import com.example.myapp.dto.TripCreationRequest;
import com.example.myapp.dto.TripResponse;
import com.example.myapp.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping 
    public ResponseEntity<List<TripResponse>> findAllTrips(@RequestParam Long userID){
        List<TripResponse> allTrips= tripService.findAllTrips(userID);
        return ResponseEntity.ok(allTrips);


    }
}
