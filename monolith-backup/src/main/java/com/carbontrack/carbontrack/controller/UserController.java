package com.carbontrack.carbontrack.controller;

import com.carbontrack.carbontrack.entity.User;
import com.carbontrack.carbontrack.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Protected endpoints that rely on the SecurityContext populated by JwtRequestFilter.
 * This controller is outside /api/auth so the JWT filter will run and populate SecurityContext.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> status() {
        User logged = JwtUtil.getLoggedInUser();

        if (logged == null) {
            // Not authenticated — return 401 so UI knows user must re-authenticate
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("authenticated", false));
        }

        return ResponseEntity.ok(Map.of(
                "authenticated", true,
                "username", logged.getUsername(),
                "userId", logged.getId()
        ));
    }
}