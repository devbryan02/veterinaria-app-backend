package com.app.veterinaria.infrastructure.mapper;

import com.app.veterinaria.domain.model.Dueno;
import com.app.veterinaria.infrastructure.persistence.entity.DuenoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DuenoMapper {

    @Mapping(target = "mascotas", expression = "java(java.util.Collections.emptyList())")
    Dueno toDomain (DuenoEntity entity);

    DuenoEntity toEntity(Dueno domain);

}
