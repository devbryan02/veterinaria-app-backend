package com.app.veterinaria.domain.valueobject;

import java.time.LocalDateTime;

/**
 * Value Object que representa un token de autenticación generado
 */
public record AuthToken(
        String token,
        LocalDateTime expiresAt,
        String tokenType
) {
    public AuthToken {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("El token no puede estar vacío");
        }
        if (tokenType == null || tokenType.isBlank()) {
            throw new IllegalArgumentException("El tipo de token no puede estar vacío");
        }
    }

    public static AuthToken of(String token, LocalDateTime expiresAt) {
        return new AuthToken(token, expiresAt, "Bearer");
    }
}