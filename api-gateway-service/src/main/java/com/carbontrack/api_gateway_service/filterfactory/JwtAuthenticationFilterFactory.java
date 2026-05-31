package com.carbontrack.api_gateway_service.filterfactory;

import com.carbontrack.api_gateway_service.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilterFactory
        extends AbstractGatewayFilterFactory<JwtAuthenticationFilterFactory.Config> {

    @Autowired
    private JwtUtil jwtUtil;

    public JwtAuthenticationFilterFactory() {
        super(Config.class);
    }

    public static class Config {
        // You can extend this later (roles, paths, etc.)
    }

    @Override
    public GatewayFilter apply(Config config) {

        return (exchange, chain) -> {

            ServerWebExchange request = exchange;

            // Skip if no Authorization header
            if (!request.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                return onError(exchange, "Missing Authorization header");
            }

            String authHeader = request.getRequest()
                    .getHeaders()
                    .getFirst(HttpHeaders.AUTHORIZATION);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return onError(exchange, "Invalid Authorization header");
            }

            String token = authHeader.substring(7);

            try {
                Claims claims = jwtUtil.extractClaims(token);
                System.out.println("Authenticated user: " + claims.getSubject());

            } catch (Exception e) {
                return onError(exchange, "Invalid or expired token");
            }

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message) {

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        System.out.println("Gateway Auth Error: " + message);

        return exchange.getResponse().setComplete();
    }
}