package com.app.veterinaria.infrastructure.web.dto.request;

public record DuenoUpdateIgnorePasswordAndLocationRequest(
        String nombre,
        String DNI,
        String direccion,
        String telefono,
        String correo
) { }
