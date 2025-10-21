package com.app.veterinaria.application.mapper;

import com.app.veterinaria.domain.model.Imagen;
import com.app.veterinaria.domain.model.Mascota;
import com.app.veterinaria.infrastructure.web.dto.request.ImagenNewRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ImagenDtoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "url", ignore = true)
    @Mapping(target = "fechaSubida", ignore = true)
    @Mapping(target = "mascota", expression = "java(mapMascota(request.mascotaId()))")
    Imagen toDomain(ImagenNewRequest request);

    //metodo auxiliar para crear una mascota a partir de su ID
    default Mascota mapMascota(String mascotaId){
        if(mascotaId == null || mascotaId.isBlank()) return null;
        Mascota mascota = new Mascota();
        mascota.setId(UUID.fromString(mascotaId));
        return mascota;
    }

}
