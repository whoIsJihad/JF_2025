package com.example.myapp.repository;

import com.example.myapp.model.CulturalTip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CulturalTipRepository extends JpaRepository<CulturalTip, Integer> {
    /**
     * Finds all cultural tips for a given city.
     */
    List<CulturalTip> findByCityId(Integer cityId);

    /**
     * Finds tips for a city that match a specific category (e.g., "Etiquette", "Dress Code").
     */
    List<CulturalTip> findByCityIdAndTipCategory(Integer cityId, String tipCategory);
}
