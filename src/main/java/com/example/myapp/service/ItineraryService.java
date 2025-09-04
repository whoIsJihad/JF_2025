package com.example.myapp.service;

import com.example.myapp.model.MeetingSlot;
import com.example.myapp.model.Trip;
import com.example.myapp.repository.TripRepository;
import com.example.myapp.service.TimeSlot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItineraryService {

    private final TripRepository tripRepository;

    @Autowired
    public ItineraryService(TripRepository tripRepository) {
        this.tripRepository = tripRepository;
    }

    /**
     * Calculates the available free time slots for a given trip.
     * This is the core algorithm for finding gaps between scheduled meetings.
     *
     * @param tripId The ID of the trip to analyze.
     * @return A List of TimeSlot objects representing the user's free time.
     */
    @Transactional(readOnly = true) // Use readOnly for performance on fetch-only operations
    public List<TimeSlot> getAvailableTimeSlots(Long tripId) {
        // 1. Fetch the Trip object from the database. Throws an error if not found.
        Trip trip = tripRepository.findById(tripId)
                .orElseThrow(() -> new RuntimeException("Trip not found with id: " + tripId));

        // 2. Data Preparation: Convert the Set<MeetingSlot> into a sorted List of our TimeSlot helper class.
        // This combines the separate date and time fields into a single, comparable LocalDateTime.
        List<TimeSlot> busySlots = trip.getMeetingSlots().stream()
                .map(meeting -> new TimeSlot(
                        meeting.getMeetingDate().atTime(meeting.getStartTime()),
                        meeting.getMeetingDate().atTime(meeting.getEndTime())
                ))
                .sorted(Comparator.comparing(TimeSlot::getStart))
                .collect(Collectors.toList());

        // 3. Algorithm Initialization
        List<TimeSlot> freeSlots = new ArrayList<>();
        LocalDateTime currentTime = trip.getStartDate().atStartOfDay(); // Pointer starts at midnight on the first day
        LocalDateTime tripEnd = trip.getEndDate().plusDays(1).atStartOfDay(); // Ends at midnight after the last day

        // 4. Main Loop: Iterate through the sorted busy slots to find the gaps.
        for (TimeSlot busySlot : busySlots) {
            // Check for a gap between our current pointer and the start of the next meeting.
            if (busySlot.getStart().isAfter(currentTime)) {
                // If there's a gap, it's a free slot. Add it to our results.
                freeSlots.add(new TimeSlot(currentTime, busySlot.getStart()));
            }
            // Move the pointer to the end of the current busy slot.
            currentTime = busySlot.getEnd();
        }

        // 5. Final Gap Check: Check for any remaining free time after the last meeting.
        if (tripEnd.isAfter(currentTime)) {
            freeSlots.add(new TimeSlot(currentTime, tripEnd));
        }

        return freeSlots;
    }
}
