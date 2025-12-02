package com.app.veterinaria.domain.model;

import com.app.veterinaria.domain.emuns.AccionEnum;
import com.app.veterinaria.domain.emuns.EntityEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record Auditoria(
        UUID id,
        UUID usuarioId,
        AccionEnum accion,
        EntityEnum entidad,
        String ipAddress,
        LocalDateTime createdAt
) {

    public static Auditoria create(
            UUID usuarioId,
            AccionEnum accion,
            EntityEnum entidad,
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