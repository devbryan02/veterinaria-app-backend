package com.app.veterinaria.infrastructure.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record DuenoNewRequest(

        @NotBlank(message = "El nombre es obligatorio")
        @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
        String nombre,

        @NotBlank(message = "El DNI es obligatorio")
        @Pattern(
                regexp = "^[0-9]{8}$",
                message = "El DNI debe contener exactamente 8 dígitos numéricos"
        )
        String DNI,

        @NotBlank(message = "La dirección es obligatoria")
        @Size(min = 5, max = 200, message = "La dirección debe tener entre 5 y 200 caracteres")
        String direccion,

        @NotBlank(message = "El teléfono es obligatorio")
        @Pattern(
                regexp = "^9[0-9]{8}$",
                message = "El teléfono debe tener 9 dígitos y comenzar con 9"
        )
        String telefono,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo no es válido")
        @Size(max = 100, message = "El correo no debe exceder 100 caracteres")
        String correo,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, max = 50, message = "La contraseña debe tener entre 8 y 50 caracteres")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "La contraseña debe contener al menos: 1 mayúscula, 1 minúscula, 1 número y 1 carácter especial"
        )
        String password,

        @NotNull(message = "La latitud es obligatoria")
        String latitud,

        @NotNull(message = "La longitud es obligatoria")
        String longitud
) { }