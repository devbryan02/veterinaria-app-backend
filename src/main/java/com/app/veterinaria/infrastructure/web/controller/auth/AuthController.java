package com.app.veterinaria.infrastructure.web.controller.auth;

import com.app.veterinaria.application.service.auth.AuthenticationService;
import com.app.veterinaria.infrastructure.web.dto.request.LoginRequest;
import com.app.veterinaria.infrastructure.web.dto.response.AuthResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest, ServerWebExchange exchange) {
        return authenticationService.login(loginRequest, exchange)
                .map(ResponseEntity::ok);
    }
    @GetMapping("/me")
    public Mono<ResponseEntity<?>> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        return authenticationService.getCurrentUserInfo(authHeader)
                .map(ResponseEntity::ok);
    }
}