package com.app.veterinaria.infrastructure.mapper;

import com.app.veterinaria.domain.model.Admin;
import com.app.veterinaria.infrastructure.persistence.entity.AdminEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdminMapper {

    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    //Mapea de entidad de domain
    Admin toDomain(AdminEntity adminEntity);

    // Mapea de Domain a entity
    AdminEntity toEntity(Admin admin);


}
