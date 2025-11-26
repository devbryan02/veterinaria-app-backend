package com.app.veterinaria.infrastructure.mapper;

import com.app.veterinaria.domain.model.Imagen;

import com.app.veterinaria.infrastructure.persistence.entity.ImagenEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ImagenMapper {

    ImagenEntity toEntity(Imagen domain);
    Imagen toDomain(ImagenEntity entity);
}
