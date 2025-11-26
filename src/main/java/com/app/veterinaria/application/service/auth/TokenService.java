package com.app.veterinaria.application.service.auth;

import com.app.veterinaria.domain.model.Rol;
import com.app.veterinaria.domain.model.Usuario;
import com.app.veterinaria.domain.valueobject.AuthToken;
import com.app.veterinaria.shared.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;

    public Mono<AuthToken> generateAuthToken(Usuario usuario) {
        return Mono.fromCallable(() -> {
            log.info("Generando tokens para usuario: {}", usuario.correo());

            // Extraer nombres de roles
            List<String> roles = usuario.roles().stream()
                    .map(Rol::nombre)
                    .collect(Collectors.toList());

            // Generar access token
            String accessToken = jwtTokenProvider.generateToken(usuario.correo(), roles);

            // Generar refresh token
            String refreshToken = jwtTokenProvider.generateRefreshToken(usuario.correo());

            // Obtener fecha de expiración
            LocalDateTime expiresAt = jwtTokenProvider.getExpirationDateFromToken(accessToken);

            log.debug("Tokens generados exitosamente para: {}", usuario.correo());

            return AuthToken.withRefresh(accessToken, refreshToken, expiresAt);
        });
    }

    public Mono<AuthToken> generateAccessToken(Usuario usuario) {
        return Mono.fromCallable(() -> {
            log.debug("Generando access token para usuario: {}", usuario.correo());

            List<String> roles = usuario.roles().stream()
                    .map(Rol::nombre)
                    .collect(Collectors.toList());

            String accessToken = jwtTokenProvider.generateToken(usuario.correo(), roles);
            LocalDateTime expiresAt = jwtTokenProvider.getExpirationDateFromToken(accessToken);

            return AuthToken.of(accessToken, expiresAt);
        });
    }

    public Mono<Boolean> validateToken(String token) {
        return Mono.fromCallable(() -> jwtTokenProvider.validateToken(token))
                .onErrorResume(error -> {
                    log.error("Error validando token: {}", error.getMessage());
                    return Mono.just(false);
                });
    }

    public Mono<String> extractUsername(String token) {
        return Mono.fromCallable(() -> jwtTokenProvider.getUsernameFromToken(token))
                .doOnError(error -> log.error("Error extrayendo username: {}", error.getMessage()));
    }

    public Mono<List<String>> extractRoles(String token) {
        return Mono.fromCallable(() -> jwtTokenProvider.getRolesFromToken(token))
                .doOnError(error -> log.error("Error extrayendo roles: {}", error.getMessage()));
    }

    public Mono<Long> getExpirationTime(String token) {
        return Mono.fromCallable(() -> jwtTokenProvider.getExpirationTimeInMillis(token))
                .doOnError(error -> log.error("Error obteniendo tiempo de expiración: {}", error.getMessage()));
    }
}