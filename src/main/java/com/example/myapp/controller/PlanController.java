package com.example.myapp.controller;

import com.example.myapp.model.PlanRequest;
import com.example.myapp.model.PlanResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PlanController {

    @PostMapping("/plan")
    public PlanResponse createPlan(@RequestBody PlanRequest request) {
        // For now, just echo the input
        String msg = "Planning for city: " + request.getCity() + ", preferences: " + request.getPreferences();
        return new PlanResponse(msg);
    }
}
