package com.example.myapp.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for sending user data back to the client.
 * This is the "receipt". Notice it has no sensitive data.
 */
@Getter
@Setter
public class UserResponse {
    private Long id;
    private String name;
    private String email;
}
