package com.example.gateway.filter;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

@Component
public class RouteValidator {

	public static final List<String> apiEndPoints = List.of("/api/v1/auth/register","/api/v1/auth/authenticate");

	public Predicate<ServerHttpRequest> isSecured = request -> apiEndPoints.stream()
			.noneMatch(uri -> request.getURI().getPath().contains(uri));
}
