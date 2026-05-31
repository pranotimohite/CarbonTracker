package com.carbontrack.auth_service.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

  /*  @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String email) {

        return Jwts.builder()
                .setSubject(email)
                .claim("userId", user.getId())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }*/

}
