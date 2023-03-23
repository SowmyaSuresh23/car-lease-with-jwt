package com.example.gateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import com.example.gateway.config.JwtService;
import com.example.gateway.exception.GatewayException;

import reactor.core.publisher.Mono;

@Component

public class JwtFilter implements GatewayFilter {

	@Autowired
	private RouteValidator routeValidator;

	@Autowired
	private JwtService jwtService;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		if (routeValidator.isSecured.test(exchange.getRequest())) {
			// header contains token or not
			if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				throw new GatewayException("Missing authorization header.");
			}

			String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			if (authHeader != null && authHeader.startsWith("Bearer ")) {
				authHeader = authHeader.substring(7);
			}
			try {
				jwtService.validateToken(authHeader);

			} catch (Exception e) {
				System.out.println("invalid access...!");
				throw new RuntimeException("un authorized access to application");
			}
		}
		return chain.filter(exchange);
	}

}
