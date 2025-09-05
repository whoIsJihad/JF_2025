package com.example.myapp.service;

import com.example.myapp.dto.AttractionDto;
import com.example.myapp.model.*;
import com.example.myapp.repository.AttractionRepository;
import com.example.myapp.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ItineraryService {

    private final TripRepository tripRepository;
    private final AttractionRepository attractionRepository;

    @Autowired
    public ItineraryService(TripRepository tripRepository, AttractionRepository attractionRepository) {
        this.tripRepository = tripRepository;
        this.attractionRepository = attractionRepository;
    }

    /**
     * Finds attractions that match a trip's preferences and are open during the user's free time.
     * This is the core scheduling and filtering logic.
     * @param tripId The ID of the trip.
     * @return A list of DTOs for available attractions.
     */
    @Transactional(readOnly = true)
    public List<AttractionDto> findAvailableAttractions(Long tripId) {
        // Step 1: Get the user's free time slots.
        List<TimeSlot> freeSlots = getAvailableTimeSlots(tripId);
        if (freeSlots.isEmpty()) {
            return Collections.emptyList();
        }

        // Step 2: Get all candidate attractions that match the user's preferences.
        List<Attraction> candidateAttractions = findAttractionsForTrip(tripId);
        if (candidateAttractions.isEmpty()) {
            return Collections.emptyList();
        }

        // Step 3: Filter the candidate attractions by availability.
        List<Attraction> availableAttractions = new ArrayList<>();
        for (Attraction attraction : candidateAttractions) {
            if (isAttractionAvailable(attraction, freeSlots)) {
                availableAttractions.add(attraction);
            }
        }

        // Step 4: Convert the final list of entities to DTOs before returning.
        return availableAttractions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * Helper method to check if a single attraction is available during any of the free time slots.
     */
    private boolean isAttractionAvailable(Attraction attraction, List<TimeSlot> freeSlots) {
        for (OpeningHours openingHour : attraction.getOpeningHours()) {
            for (TimeSlot freeSlot : freeSlots) {
                // We need to check for overlaps on each day within the free slot's range
                for (LocalDateTime day = freeSlot.getStart(); day.isBefore(freeSlot.getEnd()); day = day.plusDays(1)) {
                    DayOfWeek currentDayOfWeek = day.getDayOfWeek();
                    
                    // Convert integer day_of_week from DB to DayOfWeek enum
                    if (openingHour.getDayOfWeek() == currentDayOfWeek.getValue()) {
                        TimeSlot openingSlotOnDate = new TimeSlot(
                            day.toLocalDate().atTime(openingHour.getOpenTime()),
                            day.toLocalDate().atTime(openingHour.getCloseTime())
                        );

                        if (intervalsOverlap(openingSlotOnDate, freeSlot)) {
                            return true; // Found an overlap, this attraction is available.
                        }
                    }
                }
            }
        }
        return false; // No opening hours overlapped with any free slots.
    }
    
    /**
     * The core DSA logic to check if two time intervals overlap.
     * Two intervals do NOT overlap if one ends before the other begins.
     * @return true if they overlap, false otherwise.
     */
    private boolean intervalsOverlap(TimeSlot slotA, TimeSlot slotB) {
        // Check for the non-overlapping conditions
        boolean noOverlap = slotA.getEnd().isBefore(slotB.getStart()) ||
                            slotB.getEnd().isBefore(slotA.getStart());
        
        // If 'noOverlap' is false, it means they must overlap.
        return !noOverlap;
    }


    /**
     * Calculates the free time slots for a given trip by finding the gaps between meetings.
     */
    @Transactional(readOnly = true)
    public List<TimeSlot> getAvailableTimeSlots(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found with id: " + tripId));

        Set<MeetingSlot> meetings = trip.getMeetingSlots();

        List<TimeSlot> busySlots = meetings.stream()
                .map(meeting -> new TimeSlot(
                        meeting.getMeetingDate().atTime(meeting.getStartTime()),
                        meeting.getMeetingDate().atTime(meeting.getEndTime())
                ))
                .sorted(Comparator.comparing(TimeSlot::getStart))
                .collect(Collectors.toList());

        List<TimeSlot> freeSlots = new ArrayList<>();
        LocalDateTime currentTime = trip.getStartDate().atStartOfDay();

        for (TimeSlot busySlot : busySlots) {
            if (busySlot.getStart().isAfter(currentTime)) {
                freeSlots.add(new TimeSlot(currentTime, busySlot.getStart()));
            }
            currentTime = busySlot.getEnd();
        }

        LocalDateTime tripEnd = trip.getEndDate().plusDays(1).atStartOfDay();
        if (tripEnd.isAfter(currentTime)) {
            freeSlots.add(new TimeSlot(currentTime, tripEnd));
        }

        return freeSlots;
    }

    /**
     * Finds a list of candidate attractions based on the trip's city and preferences.
     */
    @Transactional(readOnly = true)
    public List<Attraction> findAttractionsForTrip(Long tripId) {
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found with id: " + tripId));

        Integer cityId = trip.getCity().getId();
        Set<String> preferenceNames = trip.getPreferences().stream()
                .map(PreferenceType::getName)
                .collect(Collectors.toSet());

        if (preferenceNames.isEmpty()) {
            return Collections.emptyList();
        }

        return attractionRepository.findByCityIdAndTypeIn(cityId, preferenceNames);
    }
    
    /**
     * Converts an Attraction entity to a safe AttractionDto.
     * This is done within the service to ensure lazy-loaded fields can be accessed.
     */
    private AttractionDto convertToDto(Attraction attraction) {
        AttractionDto dto = new AttractionDto();
        dto.setId(attraction.getId());
        dto.setName(attraction.getName());
        dto.setType(attraction.getType());
        dto.setAddress(attraction.getAddress());
        dto.setLocationCoords(attraction.getLocationCoords());
        dto.setAvgRating(attraction.getAvgRating());
        dto.setDescription(attraction.getDescription());
        // This access is safe because it happens inside the @Transactional method
        if (attraction.getCity() != null) {
            dto.setCityName(attraction.getCity().getName());
        }
        return dto;
    }
}

