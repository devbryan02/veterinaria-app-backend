package com.app.veterinaria.infrastructure.web.dto.request;

import jakarta.validation.constraints.*;

public record MascotaNewRequest(

        @NotBlank(message = "El id del usuario dueno es obligatorio")
        String usuarioId,

        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "El especie es obligatorio")
        String especie,

        @NotBlank(message = "La raza es obligatorio")
        String raza,

        @NotBlank(message = "El sexo es obligatorio")
        String sexo,

        @NotBlank(message = "El temperamento es obligatorio")
        String temperamento,

        @NotBlank(message = "La condicion reproductiva es obligatorio")
        String condicionReproductiva,

        @NotBlank(message = "El color es obligatorio")
        String color,
        Integer anios,

        @Min(value = 0, message = "Los meses no pueden ser negativos")
        @Max(value = 11, message = "Los meses deben ser entre 0 y 11")
        Integer meses
) { }


