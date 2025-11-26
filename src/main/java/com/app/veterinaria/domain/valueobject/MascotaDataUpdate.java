package com.app.veterinaria.domain.valueobject;

import com.app.veterinaria.domain.emuns.EstadoMascota;
import com.app.veterinaria.domain.emuns.SexoEnum;

public record MascotaDataUpdate(
        String nombre,
        String especie,
        String raza,
        SexoEnum sexo,
        String temperamento,
        String condicionReproductiva,
        String color,
        Integer anios,
        Integer meses,
        EstadoMascota estado
) { }
