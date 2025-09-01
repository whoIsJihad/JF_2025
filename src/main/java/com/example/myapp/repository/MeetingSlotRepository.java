package com.example.myapp.repository;

import com.example.myapp.model.MeetingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeetingSlotRepository extends JpaRepository<MeetingSlot, Long> {
    /**
     * Finds all meeting slots for a specific trip.
     */
    List<MeetingSlot> findByTripId(Long tripId);
}
