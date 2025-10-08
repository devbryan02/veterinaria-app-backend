package com.app.veterinaria.infrastructure.mapper;

import com.app.veterinaria.domain.model.Dueno;
import com.app.veterinaria.domain.model.Mascota;
import com.app.veterinaria.infrastructure.persistence.entity.MascotaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface MascotaMapper {

    @Mapping(target = "dueno", expression = "java(toDueno(entity.getDuenoId()))")
    @Mapping(target = "imagenes", expression = "java(java.util.Collections.emptyList())")
    Mascota toDomain(MascotaEntity entity);

    @Mapping(target = "duenoId", source = "dueno.id")
    MascotaEntity toEntity(Mascota domain);

    //Metodos helpers
    default Dueno toDueno(UUID duenoId){
        if(duenoId == null) return null;
        Dueno dueno = new Dueno();
        dueno.setId(duenoId);
        return dueno;
    }

    default UUID toDuenoId(Dueno dueno){
        return dueno != null ? dueno.getId() : null;
    }
}
