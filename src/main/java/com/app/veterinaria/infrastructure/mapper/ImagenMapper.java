package com.app.veterinaria.infrastructure.mapper;

import com.app.veterinaria.domain.model.Imagen;
import com.app.veterinaria.domain.model.Mascota;
import com.app.veterinaria.infrastructure.persistence.entity.ImagenEntity;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

public interface ImagenMapper {

    ImagenMapper INSTANCE = Mappers.getMapper(ImagenMapper.class);

    @Mapping(target = "mascota", expression = "java(toMascota(entity.getMascotaId()))")
    Imagen toDomain(ImagenEntity entity);

    @Mapping(target = "mascotaId", source = "mascota.id")
    ImagenEntity toEntity(Imagen domain);

    //metodos helpers
    default Mascota toMascota(UUID mascotaId) {
        if(mascotaId == null) return null;
        Mascota mascota = new Mascota();
        mascota.setId(mascotaId);
        return mascota;
    }

    default UUID toMascotaId(Mascota mascota) {
        return mascota != null ? mascota.getId() : null;
    }
}
