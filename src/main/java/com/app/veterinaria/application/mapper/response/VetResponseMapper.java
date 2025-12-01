package com.app.veterinaria.application.mapper.response;

import com.app.veterinaria.domain.model.Usuario;
import com.app.veterinaria.infrastructure.web.dto.details.VetInfoTable;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface VetResponseMapper {

    VetInfoTable toDetails(Usuario usuario);

}
