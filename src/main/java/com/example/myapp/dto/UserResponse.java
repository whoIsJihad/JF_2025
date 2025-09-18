package com.example.myapp.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
/**
 * DTO for sending user data back to the client.
 * This is the "receipt". Notice it has no sensitive data.
 */
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponse {
    private Long id;
    private String name;
    private String email;
}
