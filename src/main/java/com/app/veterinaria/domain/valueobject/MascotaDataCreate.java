package com.app.veterinaria.domain.valueobject;

import com.app.veterinaria.domain.emuns.SexoEnum;

import java.util.UUID;

public record MascotaDataCreate(
        UUID usuarioId,
        String nombre,
        String especie,
        String raza,
        SexoEnum sexo,
        String temperamento,
        String condicionReproductiva,
        String color,
        Integer anios,
        Integer meses
) { }
