package com.app.veterinaria.infrastructure.web.dto.details.resume;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record VacunaDetalle(
        String id,
        String tipo,
        LocalDate fechaAplicacion,
        LocalDate fechaVencimiento
) {}