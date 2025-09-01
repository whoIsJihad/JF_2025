package com.example.myapp.repository;

import com.example.myapp.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Integer> {
    /**
     * Finds a city by its name (case-insensitive).
     */
    Optional<City> findByNameIgnoreCase(String name);
}
