package com.app.veterinaria.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ImagenNewRequest(
        @NotBlank(message = "La Descripcion es obligatoria")
        String descripcion,

        @NotBlank(message = "El id de la mascota es olbligatoria")
        String mascotaId
) { }
