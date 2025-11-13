package com.carbontrack.carbontrack.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    @Value(value="${jwt.secret}")
    private String secret;
    @Value(value="${jwt.expiration}")
    private long jwtExpiration;

    public String generateToken(String username) {
        HashMap claims = new HashMap();
        return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + this.jwtExpiration)).signWith((Key)Keys.hmacShaKeyFor((byte[])this.secret.getBytes()), SignatureAlgorithm.HS256).compact();
    }

    public String extractUsername(String token) {
        return ((Claims)Jwts.parserBuilder().setSigningKey((Key)Keys.hmacShaKeyFor((byte[])this.secret.getBytes())).build().parseClaimsJws(token).getBody()).getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey((Key)Keys.hmacShaKeyFor((byte[])this.secret.getBytes())).build().parseClaimsJws(token);
            return true;
        }
        catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
