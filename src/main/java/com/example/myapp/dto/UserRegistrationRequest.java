package com.example.myapp.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for carrying new user registration data from the client.
 * This is the "order form".
 */
@Getter
@Setter
public class UserRegistrationRequest {
    private String name;
    private String email;
    private String password; // Plain text password
}
