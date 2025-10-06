package com.app.veterinaria.infrastructure.mapper;

import com.app.veterinaria.domain.model.Dueno;
import com.app.veterinaria.infrastructure.persistence.entity.DuenoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DuenoMapper {

    DuenoMapper INSTANCE = Mappers.getMapper(DuenoMapper.class);

    @Mapping(target = "mascotas", expression = "java(Collecctions.emptyList())")
    Dueno toDomain (DuenoEntity entity);

    DuenoEntity toEntity(Dueno domain);

}
