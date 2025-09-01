package com.example.myapp.repository;

import com.example.myapp.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {
    /**
     * Finds all trips associated with a specific user ID.
     */
    List<Trip> findByUserId(Long userId);
}
