package com.example.myapp.controller;
import com.example.myapp.model.User;
import com.example.myapp.dto.AuthRequest;
import com.example.myapp.dto.UserResponse;
import com.example.myapp.repository.UserRepository;
import com.example.myapp.dto.AuthResponse;
import com.example.myapp.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService; // This will be your UserService

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired 
    private  UserRepository userRepository;
    /**
     * Handles the user login request.
     * @param authRequest DTO containing the user's email and password.
     * @return A ResponseEntity containing the JWT if authentication is successful.
     */
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
        // This is the core Spring Security method that validates the email and password.
        // It will throw an exception if the credentials are bad.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        // If authentication was successful, we proceed to generate a token.
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        final String jwt = jwtUtil.generateToken(userDetails);

        final User user=userRepository.findByEmail(authRequest.getEmail()).
            orElseThrow(()->new Exception("User not found "));
        UserResponse userResponse= new UserResponse(user.getId(),user.getName(),user.getEmail());

        // Return the token in the response body.
        return ResponseEntity.ok(new AuthResponse(jwt,userResponse));
    }
}


