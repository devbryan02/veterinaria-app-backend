package com.app.veterinaria.domain.valueobject;

import java.time.LocalDate;

public record VacunaDataUpdate(
        String tipo,
        LocalDate fechaAplicacion,
        Integer mesesVigencia
) { }
