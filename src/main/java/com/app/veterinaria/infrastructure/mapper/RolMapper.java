package com.app.veterinaria.infrastructure.mapper;

import com.app.veterinaria.domain.model.Rol;
import com.app.veterinaria.infrastructure.persistence.entity.RolEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RolMapper {

    RolEntity toEntity(Rol domain);
    Rol toDomain(RolEntity entity);
}
