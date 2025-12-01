package com.app.veterinaria.domain.model;

import com.app.veterinaria.domain.emuns.AccionEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record Auditoria(
        UUID id,
        UUID usuarioId,
        AccionEnum accion,
        String entidad,
        String ipAddress,
        LocalDateTime createdAt
) {

    public static Auditoria create(
            UUID usuarioId,
            AccionEnum accion,
            String entidad,
            String ipAddress
    ) {
        return new Auditoria(
                null,
                usuarioId,
                accion,
                entidad,
                ipAddress,
                LocalDateTime.now()
        );
    }

}