package com.example.qpg.controller;

import com.example.qpg.dto.AuthDto;
import com.example.qpg.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthDto.LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AuthDto.RegisterRequest registerRequest) {
        try {
            authService.register(registerRequest);
            return ResponseEntity.ok("User registered successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
