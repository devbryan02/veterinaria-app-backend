package com.app.veterinaria.infrastructure.mapper;

import com.app.veterinaria.domain.model.Admin;
import com.app.veterinaria.infrastructure.persistence.entity.AdminEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    //Mapea de entidad de domain
    Admin toDomain(AdminEntity adminEntity);

    // Mapea de Domain a entity
    AdminEntity toEntity(Admin admin);


}
