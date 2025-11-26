package com.app.veterinaria.domain.valueobject;

import java.time.LocalDateTime;

/**
 * Value Object que representa un token de autenticación generado
 */
public record AuthToken(
        String token,
        String refreshToken,
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

    /**
     * Constructor simplificado sin refresh token
     */
    public static AuthToken of(String token, LocalDateTime expiresAt) {
        return new AuthToken(token, null, expiresAt, "Bearer");
    }

    /**
     * Constructor completo con refresh token
     */
    public static AuthToken withRefresh(String token, String refreshToken, LocalDateTime expiresAt) {
        return new AuthToken(token, refreshToken, expiresAt, "Bearer");
    }
}