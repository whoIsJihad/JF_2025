package com.example.myapp.service;

import com.example.myapp.model.PreferenceType;
import com.example.myapp.repository.PreferenceTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreferenceTypeService {

    private final PreferenceTypeRepository preferenceTypeRepository;

    @Autowired
    public PreferenceTypeService(PreferenceTypeRepository preferenceTypeRepository) {
        this.preferenceTypeRepository = preferenceTypeRepository;
    }

    /**
     * Retrieves all available preference types from the database.
     *
     * @return A list of all PreferenceType entities.
     */
    public List<PreferenceType> getAllPreferenceTypes() {
        return preferenceTypeRepository.findAll();
    }
}
