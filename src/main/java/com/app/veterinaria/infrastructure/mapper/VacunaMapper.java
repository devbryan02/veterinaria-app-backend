package com.app.veterinaria.infrastructure.mapper;

import com.app.veterinaria.domain.model.Mascota;
import com.app.veterinaria.domain.model.Vacuna;
import com.app.veterinaria.infrastructure.persistence.entity.VacunaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface VacunaMapper {

    @Mapping(target = "mascota", expression = "java(toMascota(entity.getMascotaId()))")
    Vacuna toDomain(VacunaEntity entity);

    @Mapping(target = "mascotaId", source = "mascota.id")
    VacunaEntity toEntity(Vacuna domain);

    default Mascota toMascota(UUID mascotaId){
        if(mascotaId == null)return null;
        Mascota mascota = new Mascota();
        mascota.setId(mascotaId);
        return mascota;
    }

    default UUID toMascotaId(Mascota mascota){
        return mascota != null ? mascota.getId() : null;
    }

}
