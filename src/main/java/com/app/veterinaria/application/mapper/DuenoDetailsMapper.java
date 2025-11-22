package com.app.veterinaria.application.mapper;

import com.app.veterinaria.domain.model.Dueno;
import com.app.veterinaria.domain.model.Mascota;
import com.app.veterinaria.infrastructure.web.dto.details.DuenoFullDetails;
import com.app.veterinaria.infrastructure.web.dto.details.resume.MascotaDetalle;
import com.app.veterinaria.infrastructure.web.dto.details.resume.MascotaResumen;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DuenoDetailsMapper {

    DuenoFullDetails toDetails(Dueno dueno, MascotaResumen mascota);

    MascotaDetalle toMascotaDetails(Mascota mascota);

    default MascotaResumen toResumen(List<MascotaDetalle> detalles) {
        return new MascotaResumen(detalles.size(), detalles);
    }
}

