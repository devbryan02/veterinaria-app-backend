package com.app.veterinaria.infrastructure.mapper;

import com.app.veterinaria.domain.model.Dueno;
import com.app.veterinaria.domain.model.Mascota;
import com.app.veterinaria.infrastructure.persistence.entity.MascotaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper
public interface MascotaMapper {

    MascotaMapper INSTANCE = Mappers.getMapper(MascotaMapper.class);

    @Mapping(target = "dueno", expression = "java(toDueno(entity.getDuenoId()))")
    @Mapping(target = "imagenes", expression = "java(Collecctions.emptyList())")
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
