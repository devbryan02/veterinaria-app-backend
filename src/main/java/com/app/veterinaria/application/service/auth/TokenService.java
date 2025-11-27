package com. app.veterinaria. application. service. auth;

import com.app.veterinaria.domain.model. Rol;
import com.app.veterinaria.domain.model.Usuario;
import com.app.veterinaria.domain.valueobject.AuthToken;
import com.app.veterinaria.shared.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util. Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Extrae nombres de roles del usuario
     * Manejo defensivo: si roles es null, devuelve lista vacía
     */
    private List<String> extractRoleNames(Usuario usuario) {
        return usuario. roles() == null
                ? Collections.emptyList()
                : usuario.roles().stream()
                . map(Rol::nombre)
                .collect(Collectors.toList());
    }

    /**
     * Genera AuthToken (access + refresh) para el usuario
     * METODO PRINCIPAL - Usado en AuthenticationService
     */
    public Mono<AuthToken> generateAuthToken(Usuario usuario) {
        return Mono.fromCallable(() -> {
            log.info("Generando tokens para usuario: {}", usuario.correo());

            try {
                List<String> roles = extractRoleNames(usuario);

                String accessToken = jwtTokenProvider. generateToken(usuario. correo(), roles);
                String refreshToken = jwtTokenProvider.generateRefreshToken(usuario.correo());
                LocalDateTime expiresAt = jwtTokenProvider.getExpirationDateFromToken(accessToken);

                log.debug("Tokens generados exitosamente para: {}", usuario.correo());

                return AuthToken.withRefresh(accessToken, refreshToken, expiresAt);
            } catch (Exception e) {
                log.error("Error generando tokens para usuario {}: {}", usuario.correo(), e.getMessage());
                throw e;
            }
        }). doOnError(error -> log.error("Error en generateAuthToken: {}", error. getMessage()));
    }

    /**
     * Valida el token
     * METODO PRINCIPAL - Usado en AuthenticationService
     */
    public Mono<Boolean> validateToken(String token) {
        return Mono.fromCallable(() -> jwtTokenProvider.validateToken(token))
                .onErrorResume(error -> {
                    log.error("Error validando token: {}", error.getMessage());
                    return Mono. just(false);
                });
    }

    /**
     * Extrae username del token
     * METODO PRINCIPAL - Usado en AuthenticationService
     */
    public Mono<String> extractUsername(String token) {
        return Mono.fromCallable(() -> jwtTokenProvider.getUsernameFromToken(token))
                . doOnError(error -> log.error("Error extrayendo username: {}", error. getMessage()));
    }
}