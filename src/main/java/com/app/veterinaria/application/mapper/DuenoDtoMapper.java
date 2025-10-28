package com.app.veterinaria.application.mapper;

import com.app.veterinaria.domain.model.Dueno;
import com.app.veterinaria.infrastructure.web.dto.details.DuenoDetails;
import com.app.veterinaria.infrastructure.web.dto.request.DuenoNewRequest;
import com.app.veterinaria.infrastructure.web.dto.request.DuenoUpdateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DuenoDtoMapper {

    @Mapping(target = "passwordHash", source = "password")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "mascotas", ignore = true)
    Dueno toDomain(DuenoNewRequest request);

    @Mapping(target = "cantidadMascota", source = "mascotas", qualifiedByName = "mapCantidadMascota")
    DuenoDetails toDetails(Dueno domain);

    @Named("mapCantidadMascota")
    default int mapCantidadMascota(List<?> mascotas){
        return mascotas != null ? mascotas.size() : 0;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "mascotas", ignore = true)
    void updateDuenoFromRequest(DuenoUpdateRequest request, @MappingTarget Dueno dueno);

}
