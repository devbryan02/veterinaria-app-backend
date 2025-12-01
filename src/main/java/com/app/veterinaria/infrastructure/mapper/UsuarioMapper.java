package com.app.veterinaria.infrastructure.mapper;

import com.app.veterinaria.domain.model.Usuario;
import com.app.veterinaria.infrastructure.persistence.entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioMapper {

    UsuarioEntity toEntity(Usuario domain);

    @Mapping(target = "roles", ignore = true)
    Usuario toDomain(UsuarioEntity entity);

}