package com.app.veterinaria.infrastructure.web.dto.details.resume;

import java.time.LocalDate;

public record ImagenResumen(
        String id,
        String url,
        String descripcion,
        LocalDate fechaSubida
) {}