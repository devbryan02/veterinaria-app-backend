package com.app.veterinaria.infrastructure.web.dto.request;

public record MascotaUpdateRequest(
        String nombre,
        String especie,
        String raza,
        String sexo,
        String temperamento,
        String condicionReproductiva,
        String color,
        Integer anios,
        Integer meses
) { }
