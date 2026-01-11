package com.roomreservation.controller;

import com.roomreservation.dto.AuthResponse;
import com.roomreservation.dto.SignInRequest;
import com.roomreservation.dto.SignUpRequest;
import com.roomreservation.entity.User;
import com.roomreservation.service.UserService;
import com.roomreservation.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3005", "http://localhost:3000"})
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest request) {
        try {
            // Check if user exists
            if (userService.getUserByEmail(request.getEmail()).isPresent()) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "User already exists");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
            }
            
            // Create new user
            User user = userService.createUser(request);
            
            // Generate JWT token
            String token = jwtService.generateToken(user.getEmail());
            
            // Return response
            Map<String, Object> response = new HashMap<>();
            response.put("message", "User created successfully");
            response.put("token", token);
            response.put("email", user.getEmail());
            response.put("name", user.getName());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody SignInRequest request) {
        try {
            // Find user by email
            User user = userService.getUserByEmail(request.getEmail()).orElse(null);

            // Validate credentials
            if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
                Map<String, String> error = new HashMap<>();
                error.put("error", "Invalid email or password");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
            }

            // Generate JWT token
            String token = jwtService.generateToken(user.getEmail());
            
            // Return response
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("token", token);
            response.put("email", user.getEmail());
            response.put("name", user.getName());
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
