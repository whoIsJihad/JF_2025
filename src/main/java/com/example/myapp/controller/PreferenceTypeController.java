package com.example.myapp.controller;

import com.example.myapp.model.PreferenceType;
import com.example.myapp.service.PreferenceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/preferences")
public class PreferenceTypeController {

    private final PreferenceTypeService preferenceTypeService;

    @Autowired
    public PreferenceTypeController(PreferenceTypeService preferenceTypeService) {
        this.preferenceTypeService = preferenceTypeService;
    }

    @GetMapping
    public List<PreferenceType> getAllPreferenceTypes() {
        return preferenceTypeService.getAllPreferenceTypes();
    }
}
