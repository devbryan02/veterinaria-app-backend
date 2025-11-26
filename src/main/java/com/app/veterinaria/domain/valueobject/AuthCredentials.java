package com.app.veterinaria.domain.valueobject;

public record AuthCredentials(
        String correo,
        String password
) {
    public AuthCredentials {
        if (correo == null || correo.isBlank()) {
            throw new IllegalArgumentException("El correo no puede estar vacío");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía");
        }
    }
}