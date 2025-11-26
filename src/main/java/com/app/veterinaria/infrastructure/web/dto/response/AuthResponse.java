package com.app.veterinaria.infrastructure.web.dto.response;

import java.time.LocalDateTime;
import java.util.List;

public record AuthResponse(
        String token,
        String refreshToken,
        String tokenType,
        LocalDateTime expiresAt,
        UserInfo user
) {
    public record UserInfo(
            String id,
            String nombre,
            String correo,
            List<String> roles
    ) {
    }

    public static AuthResponse of(String token, String refreshToken, LocalDateTime expiresAt,
                                  String userId, String nombre, String correo, List<String> roles) {
        return new AuthResponse(
                token,
                refreshToken,
                "Bearer",
                expiresAt,
                new UserInfo(userId, nombre, correo, roles)
        );
    }
}