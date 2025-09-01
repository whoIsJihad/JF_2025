package com.example.myapp.repository;

import com.example.myapp.model.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    /**
     * Finds all attractions in a specific city.
     */
    List<Attraction> findByCityId(Integer cityId);

    /**
     * Finds attractions in a city that match a given set of types (e.g., "Restaurant", "Museum").
     * This will be crucial for filtering based on user preferences.
     */
    List<Attraction> findByCityIdAndTypeIn(Integer cityId, Set<String> types);
}
