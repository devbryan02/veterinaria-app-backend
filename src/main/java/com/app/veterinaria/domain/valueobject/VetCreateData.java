package com.app.veterinaria.domain.valueobject;

public record VetCreateData(
        String nombre,
        String correo,
        String passwordHash,
        String telefono,
        String dni,
        String direccion
) { }
