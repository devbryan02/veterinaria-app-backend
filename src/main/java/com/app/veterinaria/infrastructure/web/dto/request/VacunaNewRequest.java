package com.app.veterinaria.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VacunaNewRequest (

        @NotBlank(message = "El id de la mascota es obligatorio")
        String mascotaId,

        @NotBlank(message = "El tipo es olbligatorio")
        String tipo,

        @NotBlank(message = "La fecha es obligatorio")
        String fechaAplicacion,

        @NotNull(message = "El mes de vigencia no debe ser nulo")
        Integer mesesVigencia
) { }
