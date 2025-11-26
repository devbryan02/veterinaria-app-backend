package com.app.veterinaria.infrastructure.web.dto.response;

import java.time.LocalDateTime;

public record TokenResponse(
        String token,
        String tokenType,
        LocalDateTime expiresAt
) {
    public static TokenResponse of(String token, LocalDateTime expiresAt) {
        return new TokenResponse(token, "Bearer", expiresAt);
    }
}