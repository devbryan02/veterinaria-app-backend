package com.app.veterinaria.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record VacunaNewRequest (

        @NotBlank(message = "El tipo es olbligatorio")
        String tipo,

        @NotBlank(message = "La fecha es obligatorio")
        String fechaAplicacion,

        @NotBlank(message = "El id de la mascota es obligatorio")
        String mascotaId,

        @NotNull(message = "El mes de vigencia no debe ser nulo")
        Integer mesesVigencia,

        @NotNull(message = "La fecha de vecimiento no debe ser nulo")
        LocalDate fechaVencimiento,

        @NotNull(message = "El proximo dosis no debe ser nulo")
        LocalDate proximaDosis
) { }
