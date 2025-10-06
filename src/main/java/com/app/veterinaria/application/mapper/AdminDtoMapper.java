package com.app.veterinaria.application.mapper;

import com.app.veterinaria.domain.model.Admin;
import com.app.veterinaria.infrastructure.web.dto.request.AdminNewRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AdminDtoMapper {

    AdminDtoMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(AdminDtoMapper.class);

    @Mapping(target = "passwordHash", source = "password")
    Admin toDomain(AdminNewRequest request);

}
