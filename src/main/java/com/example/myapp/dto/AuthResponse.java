package com.example.myapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO for the login response.
 * This class represents the JSON body the server will send back upon successful login.
 */
@Data
@AllArgsConstructor
public class AuthResponse {
    private String jwt;
    private UserResponse user;
}


