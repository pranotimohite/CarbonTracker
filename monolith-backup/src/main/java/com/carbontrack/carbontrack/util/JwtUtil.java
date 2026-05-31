
package com.carbontrack.carbontrack.util;

import com.carbontrack.carbontrack.dto.CustomUserDetails;
import com.carbontrack.carbontrack.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    // legacy - still available if needed
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + this.jwtExpiration))
                .signWith(Keys.hmacShaKeyFor(this.secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    // New: include user id as a claim so filters can avoid DB lookup for principal
    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        if (user.getId() != null) {
            claims.put("userId", user.getId());
        }
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + this.jwtExpiration))
                .signWith(Keys.hmacShaKeyFor(this.secret.getBytes()), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public Long extractUserId(String token) {
        Claims claims = getClaims(token);
        Object v = claims.get("userId");
        if (v == null) return null;
        if (v instanceof Number) return ((Number) v).longValue();
        try {
            return Long.valueOf(v.toString());
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(this.secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // keep existing getLoggedInUser static if you want to; not modified here

    public static User getLoggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }

        Object principal = auth.getPrincipal();

        if (principal instanceof CustomUserDetails) {
            return ((CustomUserDetails) principal).getUser();
        }
        return null;
    }
}

