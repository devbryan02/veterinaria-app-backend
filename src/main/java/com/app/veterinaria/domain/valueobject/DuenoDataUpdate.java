package com.app.veterinaria.domain.valueobject;

public record DuenoDataUpdate(
        String nombre,
        String correo,
        String telefono,
        String dni,
        String direccion
) { }
