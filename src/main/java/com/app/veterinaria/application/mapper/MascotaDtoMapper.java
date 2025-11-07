package com.app.veterinaria.application.mapper;

import com.app.veterinaria.domain.model.Dueno;
import com.app.veterinaria.domain.model.Mascota;
import com.app.veterinaria.infrastructure.web.dto.request.MascotaNewRequest;
import com.app.veterinaria.infrastructure.web.dto.request.MascotaUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface MascotaDtoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "imagenes", ignore = true)
    @Mapping(target = "dueno", expression = "java(mapDueno(request.duenoId()))")
    Mascota toDomain(MascotaNewRequest request);

    // Metodo auxiliar para construir el objeto Dueno a partir de su ID
    default Dueno mapDueno(String duenoId) {
        if (duenoId == null || duenoId.isBlank()) {
            return null;
        }
        Dueno dueno = new Dueno();
        dueno.setId(UUID.fromString(duenoId));
        return dueno;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dueno", ignore = true)
    @Mapping(target = "imagenes", ignore = true)
    void updateMascotaFromRequest(MascotaUpdateRequest request, @MappingTarget Mascota mascota);

}
