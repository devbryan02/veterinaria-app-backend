package com.app.veterinaria.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;

public record MascotaNewRequest(

        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @NotBlank(message = "El especie es obligatorio")
        String especie,

        @NotBlank(message = "La raza es obligatorio")
        String raza,

        @NotBlank(message = "La edad es obligatorio")
        String edad,

        @NotBlank(message = "El sexo es obligatorio")
        String sexo,

        @NotBlank(message = "El temperamento es obligatorio")
        String temperamento,

        @NotBlank(message = "La condicion reproductiva es obligatorio")
        String condicionReproductiva,

        @NotBlank(message = "El color es obligatorio")
        String color,

        @NotBlank(message = "El id del dueño es obligatorio")
        String duenoId
) { }


