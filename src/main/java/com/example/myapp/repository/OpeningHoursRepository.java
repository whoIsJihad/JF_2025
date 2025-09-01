package com.example.myapp.repository;

import com.example.myapp.model.OpeningHours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpeningHoursRepository extends JpaRepository<OpeningHours, Long> {
    /**
     * Finds all opening hour entries for a specific attraction.
     */
    List<OpeningHours> findByAttractionId(Long attractionId);
}
