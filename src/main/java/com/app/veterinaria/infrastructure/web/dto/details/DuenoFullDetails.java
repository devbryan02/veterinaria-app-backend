package com.app.veterinaria.infrastructure.web.dto.details;

import com.app.veterinaria.infrastructure.web.dto.details.resume.MascotaResumen;

public record DuenoFullDetails(
        String nombre,
        String DNI,
        String direccion,
        String telefono,
        String correo,
        String longitud,
        String latitud,
        MascotaResumen mascota
){ }
