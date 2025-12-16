package com.app.veterinaria.infrastructure.mapper;

import com.app.veterinaria.domain.model.Vacuna;
import com.app.veterinaria.infrastructure.persistence.entity.VacunaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VacunaMapper {

    VacunaEntity toEntity(Vacuna domain);
    Vacuna toDomain(VacunaEntity entity);
}