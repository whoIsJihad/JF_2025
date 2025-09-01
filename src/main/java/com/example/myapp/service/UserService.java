package com.example.myapp.service;

import com.example.myapp.dto.UserRegistrationRequest;
import com.example.myapp.dto.UserResponse;
import com.example.myapp.model.User;
import com.example.myapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse registerUser(UserRegistrationRequest requestDto) {
        // 1. Check if email already exists
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new IllegalStateException("Email already in use");
        }

        // 2. Create a new User entity
        User newUser = new User();
        newUser.setName(requestDto.getName());
        newUser.setEmail(requestDto.getEmail());
        
        // 3. Hash the password and set it on the entity
        String hashedPassword = passwordEncoder.encode(requestDto.getPassword());
        newUser.setPasswordHash(hashedPassword);

        // 4. Save the new user to the database
        User savedUser = userRepository.save(newUser);

        // 5. Create a response DTO and return it
        UserResponse response = new UserResponse();
        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());

        return response;
    }
}
