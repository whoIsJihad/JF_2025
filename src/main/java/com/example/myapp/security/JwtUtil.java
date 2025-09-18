package com.example.myapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {

    // These values will be injected from application.properties
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration.ms}")
    private long jwtExpirationMs;

    // Public method to get the username (email) from a token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Public method to check if a token is valid for a given user
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // Public method to generate a new token for a user
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    // --- Private Helper Methods ---

    private Boolean isTokenExpired(String token) {
        // Extracts the expiration date and checks if it's before the current time
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        // This is the main token builder from the jjwt library
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject) // The "subject" of the token is the user's email
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, secretKey) // Signs the token with our secret key
                .compact();
    }

    // Generic helper to extract a single piece of information (a "claim") from the
    // token
    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Helper to parse the token and get all the claims (the payload)
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}
