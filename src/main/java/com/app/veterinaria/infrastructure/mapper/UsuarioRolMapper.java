package com.app.veterinaria.infrastructure.mapper;

import com.app.veterinaria.domain.model.UsuarioRol;
import com.app.veterinaria.infrastructure.persistence.entity.UsuarioRolEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsuarioRolMapper {

    UsuarioRolEntity toEntity(UsuarioRol domain);
    UsuarioRol toDomain(UsuarioRolEntity entity);

}
