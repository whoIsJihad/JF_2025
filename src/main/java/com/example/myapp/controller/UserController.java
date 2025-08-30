package com.example.myapp.controller;

import com.example.myapp.model.User;
import com.example.myapp.service.UserService;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("api/users")

public class UserController{
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService=userService;

    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
}