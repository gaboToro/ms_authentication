package com.bullstra.logistic.authentication.controller;

import com.bullstra.logistic.authentication.client.UsersCoreClient;
import com.bullstra.logistic.authentication.dto.AuthRequestDTO;
import com.bullstra.logistic.authentication.dto.UserAuthDataDTO;
import com.bullstra.logistic.authentication.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth") // Base URL for authentication endpoints
public class AuthenticationController {

    private final JwtService jwtService;
    private final UsersCoreClient usersCoreClient;
    private final PasswordEncoder passwordEncoder;

    // Constructor for dependency injection
    public AuthenticationController(JwtService jwtService, UsersCoreClient usersCoreClient, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.usersCoreClient = usersCoreClient;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO request) {

        // 1. Fetch user authentication data (password hash, role) by email.
        UserAuthDataDTO authData = usersCoreClient.getAuthDataByEmail(request.getEmail())
                .blockOptional()
                .orElse(null);

        // 2. Check if the user was found (authData is not null).
        if (authData == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        // 3. Validate Password using the configured PasswordEncoder.
        if (passwordEncoder.matches(request.getPassword(), authData.getPasswordHash())) {

            // Success logic: Generate JWT token.
            Map<String, Object> claims = new HashMap<>();
            claims.put("userId", authData.getId().toString());
            claims.put("rolName", authData.getRolName());

            String token = jwtService.generateToken(authData.getEmail(), claims);

            // Return 200 OK with the generated token.
            return ResponseEntity.ok(token);
        } else {
            // Incorrect password. Return 401 Unauthorized.
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}