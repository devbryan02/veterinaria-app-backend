package com.app.veterinaria.infrastructure.mapper;

import com.app.veterinaria.domain.model.Vacuna;
import com.app.veterinaria.infrastructure.persistence.entity.VacunaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface VacunaMapper {

    VacunaEntity toEntity(Vacuna domain);
    Vacuna toDomain(VacunaEntity entity);
}