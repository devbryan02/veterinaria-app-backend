package com.app.veterinaria.infrastructure.web.dto.details.resume;

public record MascotaDetalle(
        String id,
        String nombre,
        String especie,
        String raza,
        String sexo
) { }
