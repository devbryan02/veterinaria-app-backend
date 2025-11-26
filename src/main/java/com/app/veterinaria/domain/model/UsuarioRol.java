package com.app.veterinaria.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioRol(
        UUID id,
        UUID usuarioId,
        UUID rolId,
        LocalDateTime asignadoEn
) {
    public static UsuarioRol create(UUID usuarioId, UUID rolId) {
        return new UsuarioRol(null, usuarioId, rolId, null);
    }
}
