package com.app.veterinaria.infrastructure.web.dto.details;

import java.time.LocalDate;
import java.util.UUID;

public record VacunaWithMascotaDetails(
        UUID id,
        String tipo,
        LocalDate fechaaplicacion,
        String mascota,
        Integer mesesvigencia,
        LocalDate fechavencimiento,
        LocalDate proximadosis
)
{ }
