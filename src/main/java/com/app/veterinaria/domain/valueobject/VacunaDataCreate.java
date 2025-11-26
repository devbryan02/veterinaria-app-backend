package com.app.veterinaria.domain.valueobject;

import java.time.LocalDate;
import java.util.UUID;

public record VacunaDataCreate(
        UUID mascotaId,
        String tipo,
        LocalDate fechaAplicacion,
        Integer mesesVigencia
) { }
