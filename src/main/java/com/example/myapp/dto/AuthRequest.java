package com.example.myapp.dto;

import lombok.Data;

/**
 * DTO (Data Transfer Object) for the login request.
 * This class represents the JSON body the client will send when logging in.
 */
@Data
public class AuthRequest {
    private String email;
    private String password;
}


