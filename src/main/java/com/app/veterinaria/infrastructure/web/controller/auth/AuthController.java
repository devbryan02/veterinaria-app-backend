package com.app.veterinaria.infrastructure.web.controller.auth;

import com.app.veterinaria.application.service.auth.AuthenticationService;
import com.app.veterinaria.infrastructure.web.dto.request.LoginRequest;
import com.app.veterinaria.infrastructure.web.dto.request.RegisterRequest;
import com.app.veterinaria.infrastructure.web.dto.response.AuthResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        return authenticationService.login(loginRequest)
                .map(ResponseEntity::ok);
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<AuthResponse>> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return authenticationService.register(registerRequest)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @PostMapping("/refresh")
    public Mono<ResponseEntity<AuthResponse>> refreshToken(@RequestHeader("Authorization") String authHeader) {
        return authenticationService.refreshToken(authHeader)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/me")
    public Mono<ResponseEntity<?>> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
        return authenticationService.getCurrentUserInfo(authHeader)
                .map(ResponseEntity::ok);
    }
}