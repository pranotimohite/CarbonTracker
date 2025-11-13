package com.carbontrack.carbontrack.config;

import com.carbontrack.carbontrack.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtRequestFilter
extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String jwt;
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ") && this.jwtUtil.validateToken(jwt = authHeader.substring(7))) {
            String username = this.jwtUtil.extractUsername(jwt);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken((Object)new User(username, "", Collections.emptyList()), null, Collections.emptyList());
            authentication.setDetails((Object)new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication((Authentication)authentication);
        }
        chain.doFilter((ServletRequest)request, (ServletResponse)response);
    }
}