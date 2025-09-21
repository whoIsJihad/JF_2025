package com.example.myapp.service ;

import com.example.myapp.repository.AttractionRepository;
import com.example.myapp.model.Attraction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service

public class AttractionService {

    @Autowired
    private AttractionRepository attractionRepository;

    public List<Attraction> getAllAttractions() {
        return attractionRepository.findAll();
    }
}