package com.example.myapp.controller;
import com.example.myapp.dto.UserRegistrationRequest;
import com.example.myapp.dto.UserResponse;
import com.example.myapp.service.UserService;
import com.example.myapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRegistrationRequest request){
        UserResponse userResponse = userService.registerUser(request);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails){
        User user=userService.findUserByEmail(userDetails.getUsername());
        UserResponse userResponse=new UserResponse(user.getId(),user.getName(),user.getEmail());

        return ResponseEntity.ok(userResponse);
    }
}