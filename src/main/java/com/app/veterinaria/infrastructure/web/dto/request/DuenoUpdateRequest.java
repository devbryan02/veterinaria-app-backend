package com.app.veterinaria.infrastructure.web.dto.request;

public record DuenoUpdateRequest(
        String nombre,
        String DNI,
        String direccion,
        String telefono,
        String correo,
        String password,
        String latitud,
        String longitud
)
{ }
