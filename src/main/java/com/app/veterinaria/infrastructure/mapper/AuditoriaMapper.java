package com.app.veterinaria.infrastructure.mapper;


import com.app.veterinaria.domain.model.Auditoria;
import com.app.veterinaria.infrastructure.persistence.entity.AuditoriaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,  unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuditoriaMapper {

    Auditoria toDomain(AuditoriaEntity entity);
    AuditoriaEntity toEntity(Auditoria domain);

}
