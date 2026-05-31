package com.carbontrack.auth_service.service;

import com.carbontrack.auth_service.dto.*;
import com.carbontrack.auth_service.entity.User;
import com.carbontrack.auth_service.repository.UserRepository;
import com.carbontrack.auth_service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String register(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");

        userRepository.save(user);

        return "User registered successfully";
    }

    public AuthResponse login(AuthRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateToken(user); // simplified

        return new AuthResponse(accessToken, refreshToken);
    }
}

/*
import com.carbontrack.auth_service.dto.AuthResponse;
import com.carbontrack.auth_service.dto.AuthRequest;
import com.carbontrack.auth_service.dto.RegisterRequest;
import com.carbontrack.auth_service.entity.User;
import com.carbontrack.auth_service.repository.UserRepository;
import com.carbontrack.auth_service.util.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse login(AuthRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token);
    }

    public void register(RegisterRequest request) {

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }
}
*/
