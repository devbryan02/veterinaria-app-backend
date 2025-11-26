package com.app.veterinaria.infrastructure.mapper;

import com.app.veterinaria.domain.model.Auditoria;
import com.app.veterinaria.infrastructure.persistence.entity.AuditoriaEntity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;
import java.util.Map;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuditoriaMapper {

    ObjectMapper JSON = new ObjectMapper();

    @Mapping(target = "datosAnteriores", source = "datosAnteriores", qualifiedByName = "mapToJson")
    @Mapping(target = "datosNuevos", source = "datosNuevos", qualifiedByName = "mapToJson")
    AuditoriaEntity toEntity(Auditoria domain);

    @Mapping(target = "datosAnteriores", source = "datosAnteriores", qualifiedByName = "jsonToMap")
    @Mapping(target = "datosNuevos", source = "datosNuevos", qualifiedByName = "jsonToMap")
    Auditoria toDomain(AuditoriaEntity entity);

    @Named("mapToJson")
    default String mapToJson(Map<String, Object> map) {
        if (map == null) return null;
        try {
            return JSON.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Named("jsonToMap")
    default Map<String, Object> jsonToMap(String json) {
        if (json == null || json.isBlank()) return null;
        try {
            return JSON.readValue(json, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}