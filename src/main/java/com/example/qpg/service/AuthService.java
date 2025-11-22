package com.example.qpg.service;

import com.example.qpg.dto.AuthDto;
import com.example.qpg.model.User;
import com.example.qpg.repository.UserRepository;
import com.example.qpg.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    public AuthDto.AuthResponse login(AuthDto.LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);
        
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow();

        return new AuthDto.AuthResponse(jwt, user.getRole().name());
    }

    public void register(AuthDto.RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        
        if ("ADMIN".equalsIgnoreCase(registerRequest.getRole())) {
            user.setRole(User.Role.ROLE_ADMIN);
        } else {
            user.setRole(User.Role.ROLE_TEACHER);
        }

        userRepository.save(user);
    }
}
