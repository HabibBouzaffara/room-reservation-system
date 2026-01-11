package com.roomreservation.controller;

import com.roomreservation.dto.AuthResponse;
import com.roomreservation.dto.SignInRequest;
import com.roomreservation.dto.SignUpRequest;
import com.roomreservation.entity.User;
import com.roomreservation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest request) {
        try {
            User user = userService.createUser(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(AuthResponse.UserDTO.fromUser(user));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody SignInRequest request) {
        User user = userService.getUserByEmail(request.getEmail())
                .orElse(null);

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        // For now, return user info (JWT will be added in security config)
        return ResponseEntity.ok(AuthResponse.builder()
                .accessToken("temp-token")
                .tokenType("Bearer")
                .expiresIn(86400L)
                .user(AuthResponse.UserDTO.fromUser(user))
                .build());
    }
}
