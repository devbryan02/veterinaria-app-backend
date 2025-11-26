package com.app.veterinaria.domain.model;

import com.app.veterinaria.domain.emuns.AccionEnum;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public record Auditoria(
        UUID id,
        UUID usuarioId,
        AccionEnum accion,
        String entidad,
        UUID entidadId,
        Map<String, Object> datosAnteriores,
        Map<String, Object> datosNuevos,
        String ipAddress,
        LocalDateTime createdAt
) {
}