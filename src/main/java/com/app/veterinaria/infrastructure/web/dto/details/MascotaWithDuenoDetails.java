package com.app.veterinaria.infrastructure.web.dto.details;

import java.util.UUID;

public record MascotaWithDuenoDetails(
        UUID id,
        String nombre,
        String especie,
        String raza,
        String edad,
        String sexo,
        String temperamento,
        String condicionReproductiva,
        String color,
        String dueno
) { }
