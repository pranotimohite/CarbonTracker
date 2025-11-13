package com.carbontrack.carbontrack.controller;

import com.carbontrack.carbontrack.util.JwtUtil;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/auth"})
public class AuthController {
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(value={"/login"})
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String token = this.jwtUtil.generateToken(username);
        return Map.of("token", token);
    }
}