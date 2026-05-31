package com.carbontrack.api_gateway_service.filter;

import com.carbontrack.api_gateway_service.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter implements GlobalFilter, Ordered {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        // Skip auth endpoints
        if (path.startsWith("/api/auth")) {
            return chain.filter(exchange);
        }

        // Get Authorization header
        if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String authHeader = exchange.getRequest()
                .getHeaders()
                .getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        String token = authHeader.substring(7);

        ServerWebExchange modifiedExchange;
        try {
            Claims claims = jwtUtil.extractClaims(token);
            log.info("Authenticated user: {}", claims.getSubject());

            // Forward identity to downstream services
            modifiedExchange = exchange.mutate()
                    .request(r -> r.headers(headers -> {
                        headers.add("X-USER", claims.getSubject());
                        headers.add("X-USER-ID", claims.get("userId", String.class));
                        headers.add("X-ROLE", String.valueOf(claims.get("role")));
                    }))
                    .build();

        } catch (Exception e) {
            log.warn("Invalid JWT token", e);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(modifiedExchange);
    }

    @Override
    public int getOrder() {
        return -1; // high priority filter
    }
}