package com.example.myapp.repository;

import com.example.myapp.model.ItineraryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItineraryItemRepository extends JpaRepository<ItineraryItem, Long> {
    /**
     * Finds all itinerary items for a specific trip, allowing you to reconstruct a user's plan.
     */
    List<ItineraryItem> findByTripId(Long tripId);
}
