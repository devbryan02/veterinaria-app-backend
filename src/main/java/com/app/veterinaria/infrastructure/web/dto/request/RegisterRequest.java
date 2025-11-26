package com.app.veterinaria.infrastructure.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest (
        @NotBlank(message = "El nombre es obligatorio")
        String nombre,

        @Email(message = "El correo deber ser valido")
        @NotBlank(message = "El correo es obligatorio")
        String correo,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, max = 50, message = "La contraseña debe tener entre 8 y 50 caracteres")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
                message = "La contraseña debe contener al menos: 1 mayúscula, 1 minúscula, 1 número y 1 carácter especial"
        )
        String password,

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
){ }
