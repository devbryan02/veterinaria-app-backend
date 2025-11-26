package com.app.veterinaria.infrastructure.mapper;

import com.app.veterinaria.domain.model.Mascota;
import com.app.veterinaria.domain.emuns.SexoEnum;
import com.app.veterinaria.infrastructure.persistence.entity.MascotaEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MascotaMapper {

    @Mapping(target = "sexo", source = "sexo", qualifiedByName = "sexoToString")
    MascotaEntity toEntity(Mascota domain);

    @Mapping(target = "sexo", source = "sexo", qualifiedByName = "stringToSexo")
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "imagenes", ignore = true)
    @Mapping(target = "actualizar", ignore = true)
    Mascota toDomain(MascotaEntity entity);

    @Named("sexoToString")
    default String sexoToString(SexoEnum sexo) {
        return sexo != null ? sexo.name() : null;
    }

    @Named("stringToSexo")
    default SexoEnum stringToSexo(String sexo) {
        return sexo != null ? SexoEnum.valueOf(sexo) : null;
    }
}

