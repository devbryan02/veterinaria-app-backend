package com.app.veterinaria.domain.model;

import java.util.UUID;

public record Rol(
        UUID id,
        String nombre,
        String descripcion
) {
    public static Rol create(String nombre, String descripcion) {
        return new Rol(null, nombre, descripcion);
    }
}

