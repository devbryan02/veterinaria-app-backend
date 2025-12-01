package com.app.veterinaria.infrastructure.web.dto.details;


import java.util.UUID;

public record VetInfoTable(
        UUID id,
        String nombre,
        String correo,
        String telefono,
        String dni,
        Boolean activo,
        Boolean cuentaNoBloqueada
)
{ }
