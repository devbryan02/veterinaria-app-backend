package com.app.veterinaria.infrastructure.mapper;


import com.app.veterinaria.domain.model.Auditoria;
import com.app.veterinaria.infrastructure.persistence.entity.AuditoriaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuditoriaMapper {

    Auditoria toDomain(AuditoriaEntity entity);

    AuditoriaEntity toEntity(Auditoria domain);

}
