package com.example.myapp.service;

import com.example.myapp.dto.*;
import com.example.myapp.model.*;
import com.example.myapp.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final PreferenceTypeRepository preferenceTypeRepository;
    private final MeetingSlotRepository meetingSlotRepository;

    @Autowired
    public TripService(TripRepository tripRepository, UserRepository userRepository, CityRepository cityRepository, PreferenceTypeRepository preferenceTypeRepository, MeetingSlotRepository meetingSlotRepository) {
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.preferenceTypeRepository = preferenceTypeRepository;
        this.meetingSlotRepository = meetingSlotRepository;
    }

    @Transactional
    public TripResponse createTrip(TripCreationRequest request) {
        // 1. Fetch related entities
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + request.getUserId()));

        City city = cityRepository.findByNameIgnoreCase(request.getCityName())
                .orElseThrow(() -> new IllegalArgumentException("City not found: " + request.getCityName()));

        Set<PreferenceType> preferences = new HashSet<>(preferenceTypeRepository.findByNameIn(request.getPreferenceNames()));
        if (preferences.size() != request.getPreferenceNames().size()) {
            throw new IllegalArgumentException("One or more preferences are invalid.");
        }

        // 2. Create and save the main Trip entity
        Trip newTrip = new Trip();
        newTrip.setUser(user);
        newTrip.setCity(city);
        newTrip.setStartDate(request.getStartDate());
        newTrip.setEndDate(request.getEndDate());
        newTrip.setHotelLocation(request.getHotelLocation());
        newTrip.setPreferences(preferences);

        Trip savedTrip = tripRepository.save(newTrip);

        // 3. Create and save the associated MeetingSlots
        List<MeetingSlot> meetingSlots = request.getMeetingSlots().stream()
                .map(dto -> {
                    MeetingSlot slot = new MeetingSlot();
                    slot.setTrip(savedTrip); // Link to the saved trip
                    slot.setMeetingDate(dto.getMeetingDate());
                    slot.setStartTime(dto.getStartTime());
                    slot.setEndTime(dto.getEndTime());
                    return slot;
                })
                .collect(Collectors.toList());
        
        meetingSlotRepository.saveAll(meetingSlots);

        // 4. Build and return the response DTO
        return buildTripResponse(savedTrip);
    }

    private TripResponse buildTripResponse(Trip trip) {
        TripResponse response = new TripResponse();
        response.setId(trip.getId());
        response.setHotelLocation(trip.getHotelLocation());
        response.setStartDate(trip.getStartDate());
        response.setEndDate(trip.getEndDate());

        if (trip.getCity() != null) {
            response.setCityName(trip.getCity().getName());
            response.setCountry(trip.getCity().getCountry());
        }

        if (trip.getUser() != null) {
            UserResponse userDto = new UserResponse();
            userDto.setId(trip.getUser().getId());
            userDto.setName(trip.getUser().getName());
            userDto.setEmail(trip.getUser().getEmail());
            response.setUser(userDto);
        }

        Set<String> prefNames = trip.getPreferences().stream()
                .map(PreferenceType::getName)
                .collect(Collectors.toSet());
        response.setPreferences(prefNames);

        return response;
    }
}
