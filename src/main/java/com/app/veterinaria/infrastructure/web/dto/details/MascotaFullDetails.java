package com.app.veterinaria.infrastructure.web.dto.details;

import com.app.veterinaria.domain.emuns.EstadoMascota;
import com.app.veterinaria.infrastructure.web.dto.details.resume.DuenoResumen;
import com.app.veterinaria.infrastructure.web.dto.details.resume.ImagenResumen;
import com.app.veterinaria.infrastructure.web.dto.details.resume.VacunasResumen;

import java.time.LocalDate;
import java.util.List;

public record MascotaFullDetails(
        String id,
        String nombre,
        String especie,
        String raza,
        String edad,
        String sexo,
        String color,
        String identificador,
        String fotoUrl, //primera imagen
        EstadoMascota estado,
        List<ImagenResumen> imagenList,
        DuenoResumen dueno,
        VacunasResumen vacuna
) { }




