package com.app.veterinaria.infrastructure.web.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DuenoUpdateRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
        String nombre,

        @NotBlank(message = "La dirección es obligatoria")
        @Size(min = 5, max = 200, message = "La dirección debe tener entre 5 y 200 caracteres")
        String correo,

        @NotBlank(message = "El teléfono es obligatorio")
        @Pattern(
                regexp = "^9[0-9]{8}$",
                message = "El teléfono debe tener 9 dígitos y comenzar con 9"
        )

        String telefono,

        @NotBlank(message = "El DNI es obligatorio")
        @Pattern(
                regexp = "^[0-9]{8}$",
                message = "El DNI debe contener exactamente 8 dígitos numéricos"
        )
        String dni,

        @NotBlank(message = "La dirección es obligatoria")
        @Size(min = 5, max = 200, message = "La dirección debe tener entre 5 y 200 caracteres")
        String direccion
) { }
