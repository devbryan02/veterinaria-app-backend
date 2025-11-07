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

        @NotBlank(message = "El mes de vigencia es Obligatorio")
        Integer mesesVigencia,

        @NotBlank(message = "El fecha de vencimiento es obligatorio")
        LocalDate fechaVencimiento,

        @NotBlank(message = "La proxima dosis es obligatorio")
        LocalDate proximaDosis
) { }
