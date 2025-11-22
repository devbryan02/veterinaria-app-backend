package com.app.veterinaria.application.mapper;

import com.app.veterinaria.domain.model.*;
import com.app.veterinaria.infrastructure.web.dto.details.*;
import com.app.veterinaria.infrastructure.web.dto.details.resume.DuenoResumen;
import com.app.veterinaria.infrastructure.web.dto.details.resume.ImagenResumen;
import com.app.veterinaria.infrastructure.web.dto.details.resume.VacunaDetalle;
import com.app.veterinaria.infrastructure.web.dto.details.resume.VacunasResumen;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MascotaDetailsMapper {

    @Mapping(source = "mascota.id", target = "id")
    @Mapping(source = "mascota.nombre", target = "nombre")
    @Mapping(source = "mascota.especie", target = "especie")
    @Mapping(source = "mascota.raza", target = "raza")
    @Mapping(source = "edad", target = "edad")
    @Mapping(source = "fotoPrincipal", target = "fotoUrl")
    @Mapping(source = "mascota.sexo", target = "sexo")
    @Mapping(source = "mascota.color", target = "color")
    @Mapping(source = "mascota.identificador", target = "identificador")
    @Mapping(source = "mascota.estado", target = "estado")
    @Mapping(source = "imagenes", target = "imagenList")
    @Mapping(source = "vacunas", target = "vacuna")
    @Mapping(source = "dueno", target = "dueno")
    MascotaFullDetails toDetails(
            Mascota mascota,
            DuenoResumen dueno,
            VacunasResumen vacunas,
            List<ImagenResumen> imagenes,
            String edad,
            String fotoPrincipal
    );

    DuenoResumen toDuenoResumen(Dueno dueno);
    ImagenResumen toImagenResumen(Imagen imagen);
    VacunaDetalle toVacunaDetalle(Vacuna vacuna);
}

