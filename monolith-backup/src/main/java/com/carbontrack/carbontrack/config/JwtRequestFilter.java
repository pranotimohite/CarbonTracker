package com.carbontrack.carbontrack.config;

import com.carbontrack.carbontrack.dto.CustomUserDetails;
import com.carbontrack.carbontrack.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);
    private final JwtUtil jwtUtil;

    public JwtRequestFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        try {
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                String token = resolveToken(request);

                if (token != null && jwtUtil.validateToken(token)) {
                    String username = jwtUtil.extractUsername(token);
                    Long userId = jwtUtil.extractUserId(token); // may be null if token pre-dates change

                    // Build a minimal domain User with id+username so CustomUserDetails works
                    com.carbontrack.carbontrack.entity.User domainUser = new com.carbontrack.carbontrack.entity.User();
                    domainUser.setUsername(username);
                    if (userId != null) {
                        domainUser.setId(userId);
                    }

                    CustomUserDetails cud = new CustomUserDetails(domainUser);

                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            cud, null, Collections.emptyList());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (Exception ex) {
            logger.warn("Failed to process JWT authentication: {}", ex.getMessage());
            SecurityContextHolder.clearContext();
        }

        chain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7).trim();
        }

        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if ("jwt".equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/actuator") || path.startsWith("/api/auth");
    }
}