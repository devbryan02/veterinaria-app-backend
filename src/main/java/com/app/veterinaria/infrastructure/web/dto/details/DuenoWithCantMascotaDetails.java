package com.app.veterinaria.infrastructure.web.dto.details;

import java.util.UUID;

public record DuenoWithCantMascotaDetails(
        UUID id,
        String nombre,
        String dni,
        String direccion,
        String telefono,
        String correo,
        int cantidadmascotas
) { }
