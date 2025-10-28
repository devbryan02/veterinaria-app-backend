package com.app.veterinaria.infrastructure.web.dto.details;

import java.util.UUID;

public record DuenoDetails(
        UUID id,
        String nombre,
        String DNI,
        String direccion,
        String telefono,
        String correo,
        int cantidadMascota
) { }
