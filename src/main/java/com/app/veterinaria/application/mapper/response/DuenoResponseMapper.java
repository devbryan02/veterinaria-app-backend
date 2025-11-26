package com.app.veterinaria.application.mapper.response;

import com.app.veterinaria.domain.model.Mascota;
import com.app.veterinaria.domain.model.Usuario;
import com.app.veterinaria.infrastructure.web.dto.details.DuenoFullDetails;
import com.app.veterinaria.infrastructure.web.dto.details.resume.MascotaDetalle;
import com.app.veterinaria.infrastructure.web.dto.details.resume.MascotaResumen;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DuenoResponseMapper {

    DuenoFullDetails toDetails(Usuario usuario, MascotaResumen mascota);

    MascotaDetalle toMascotaDetails(Mascota mascota);

    default MascotaResumen toResumen(List<MascotaDetalle> detalles) {
        return new MascotaResumen(detalles.size(), detalles);
    }
}

