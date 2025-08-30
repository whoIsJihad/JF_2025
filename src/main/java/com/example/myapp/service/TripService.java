package com.example.myapp.service;

import com.example.myapp.model.*;
import com.example.myapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripService {
    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    @Autowired
    public TripService(TripRepository tripRepository, UserRepository userRepository) {
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
    }


    public Trip createTrip(Trip trip,Long userID){
       User user = userRepository.findById(userID)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userID));

    // Step 2: Associate the found user with the trip.
    trip.setUser(user);

    // Step 3: Save the complete trip object to the database and return it.
    return tripRepository.save(trip);
    }
}