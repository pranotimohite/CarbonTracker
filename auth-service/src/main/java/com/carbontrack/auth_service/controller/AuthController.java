package com.carbontrack.auth_service.controller;

import com.carbontrack.auth_service.dto.*;
import com.carbontrack.auth_service.dto.AuthRequest;
import com.carbontrack.auth_service.dto.RegisterRequest;
import com.carbontrack.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }
}



/*
import com.carbontrack.auth_service.dto.AuthResponse;
import com.carbontrack.auth_service.dto.RegisterRequest;
import com.carbontrack.auth_service.service.AuthService;
import com.carbontrack.carbontrack.entity.User;
import com.carbontrack.carbontrack.repository.UserRepository;
import com.carbontrack.carbontrack.util.JwtUtil;
import java.util.Map;
import java.util.Optional;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value={"/api/auth"})
public class AuthController {

  */
/*  @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

//    Map<String, String>

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validate() {
        return ResponseEntity.ok(true);
    }

    @PostMapping(value={"/login"})
    public ResponseEntity<?> login(@RequestBody Map<String, String> request, HttpServletResponse response) {
        String username = request.get("username");
        if (username == null || username.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "username is required"));
        }

        // Ensure domain User exists (create if necessary) so we have an id for token claims
        Optional<User> userOpt = userRepository.findByUsername(username);
        User user = userOpt.orElseGet(() -> userRepository.save(new User(username)));

        String token = this.jwtUtil.generateToken(user);

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);   // set true in production when using HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(86400); // 24 hours in seconds
        response.addCookie(cookie);

        return ResponseEntity.ok(Map.of("message", "Login successful"));
    }

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> status(HttpServletRequest request) {
        // Look for the "jwt" cookie
        if (request.getCookies() == null) {
            return ResponseEntity.ok(Map.of("authenticated", false));
        }

        String token = null;
        for (jakarta.servlet.http.Cookie c : request.getCookies()) {
            if ("jwt".equals(c.getName())) {
                token = c.getValue();
                break;
            }
        }

        if (token == null || token.isBlank()) {
            return ResponseEntity.ok(Map.of("authenticated", false));
        }

        boolean valid = jwtUtil.validateToken(token);
        if (!valid) {
            return ResponseEntity.ok(Map.of("authenticated", false));
        }

        // Token is valid — extract username and optional userId
        String username = jwtUtil.extractUsername(token);
        Long userId = jwtUtil.extractUserId(token);

        Map<String, Object> body = Map.of(
                "authenticated", true,
                "username", username != null ? username : "",
                "userId", userId
        );

        return ResponseEntity.ok(body);
    }*//*

}*/
