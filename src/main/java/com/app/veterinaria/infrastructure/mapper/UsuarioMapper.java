package com.app.veterinaria.infrastructure.mapper;

import com.app.veterinaria.domain.model.Usuario;
import com.app.veterinaria.infrastructure.persistence.entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UsuarioMapper {

    UsuarioEntity toEntity(Usuario domain);

    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "actualizar", ignore = true)
    Usuario toDomain(UsuarioEntity entity);

}