package com.carbontrack.auth_service.service;

import com.carbontrack.auth_service.dto.*;
import com.carbontrack.auth_service.entity.User;
import com.carbontrack.auth_service.repository.UserRepository;
import com.carbontrack.auth_service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final JwtService jwtService;
    @Autowired
    private final KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public String register(RegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");

        userRepository.save(user);

        // publish event (non-blocking)
        try {
            UserCreatedEvent event = new UserCreatedEvent(user.getId(), user.getUsername(), user.getRole());
            kafkaTemplate.send("user.created", String.valueOf(user.getId()), event);
            log.info("Published UserCreatedEvent for userId={}", user.getId());
        } catch (Exception ex) {
            log.error("Failed to publish UserCreatedEvent for userId={}", user.getId(), ex);
            // decide: schedule retry / alert / accept eventual consistency
        }

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

//    @Autowired
//    private KafkaTemplate<String, UserCreatedEvent> kafkaTemplate;
//
//    public void publishUserCreated(User user) {
//        UserCreatedEvent e = new UserCreatedEvent(user.getId(), user.getUsername(), user.getRole());
//        kafkaTemplate.send("user.created", String.valueOf(user.getId()), e);
//    }
}