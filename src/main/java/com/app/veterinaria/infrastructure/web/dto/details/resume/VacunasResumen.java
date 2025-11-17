package com.app.veterinaria.infrastructure.web.dto.details.resume;

import java.util.List;

public record VacunasResumen(
        Integer totalVacunas,
        List<VacunaDetalle> vacunaslist
) {}

