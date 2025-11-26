package com.app.veterinaria.domain.valueobject;

public record DuenoDataCreate(
        String nombre,
        String correo,
        String passwordHash,
        String telefono,
        String dni,
        String direccion,
        String latitud,
        String longitud
){}
