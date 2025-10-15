package com.app.veterinaria.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record VacunaNewRequest (

        @NotBlank(message = "El tipo es olbligatorio")
        String tipo,

        @NotBlank(message = "La fecha es obligatorio")
        String fechaAplicacion,

        @NotBlank(message = "El id de la mascota es obligatorio")
        String mascotaId
) { }
