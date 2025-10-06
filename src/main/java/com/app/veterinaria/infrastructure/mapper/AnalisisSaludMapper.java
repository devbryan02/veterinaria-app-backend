package com.app.veterinaria.infrastructure.mapper;

import com.app.veterinaria.domain.model.Admin;
import com.app.veterinaria.domain.model.AnalisisSalud;
import com.app.veterinaria.domain.model.Mascota;
import com.app.veterinaria.domain.record.ResultAI;
import com.app.veterinaria.infrastructure.persistence.entity.AnalisisSaludEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.UUID;

@Mapper
public interface AnalisisSaludMapper {

    // Instancia única del mapper que MapStruct genera automáticamente
    AnalisisSaludMapper INSTANCE = Mappers.getMapper(AnalisisSaludMapper.class);

    // ----------------------
    // Entity -> Domain
    // ----------------------
    // Convierte un AnalisisSaludEntity (BD) a AnalisisSalud (Domain).
    // - mascotaId (UUID) se convierte en un objeto Mascota (con solo el id cargado).
    // - adminId (UUID) se convierte en un objeto Admin (con solo el id cargado).
    // - resultado (JSON String) se convierte en un objeto ResultAI.
    @Mapping(target = "mascota", expression = "java(toMascota(entity.getMascotaId()))")
    @Mapping(target = "admin", expression = "java(toAdmin(entity.getAdminId()))")
    @Mapping(target = "resultado", expression = "java(toResultAI(entity.getResultado()))")
    AnalisisSalud toDomain(AnalisisSaludEntity entity);

    // ----------------------
    // Domain -> Entity
    // ----------------------
    // Convierte un AnalisisSalud (Domain) a AnalisisSaludEntity (BD).
    // - mascota.id se convierte en mascotaId (UUID).
    // - admin.id se convierte en adminId (UUID).
    // - resultado (ResultAI) se serializa a JSON para guardarlo como String.
    @Mapping(target = "mascotaId", source = "mascota.id")
    @Mapping(target = "adminId", source = "admin.id")
    @Mapping(target = "resultado", expression = "java(toResultadoJson(domain.getResultado()))")
    AnalisisSaludEntity toEntity(AnalisisSalud domain);

    // ----------------------
    // Helpers para Mascota
    // ----------------------
    // Convierte un UUID (mascotaId) en un objeto Mascota con solo el id cargado.
    default Mascota toMascota(UUID mascotaId){
        if(mascotaId == null) return null;
        Mascota mascota = new Mascota();
        mascota.setId(mascotaId);
        return mascota;
    }

    // Convierte un objeto Mascota en su UUID (id) para guardar en la BD.
    default UUID toMascotaId(Mascota mascota){
        return mascota != null ? mascota.getId() : null;
    }

    // ----------------------
    // Helpers para Admin
    // ----------------------
    // Convierte un UUID (adminId) en un objeto Admin con solo el id cargado.
    default Admin toAdmin(UUID adminId){
        if(adminId == null) return null;
        Admin admin = new Admin();
        admin.setId(adminId);
        return admin;
    }

    // Convierte un objeto Admin en su UUID (id) para guardar en la BD.
    default UUID toAdminId(Admin admin){
        return admin != null ? admin.getId() : null;
    }

    // ----------------------
    // Helpers para ResultAI (JSON <-> Objeto)
    // ----------------------
    // Convierte un String JSON guardado en la BD a un objeto ResultAI.
    default ResultAI toResultAI(String resultadoJson){
        if(resultadoJson == null) return null;
        try {
            return new ObjectMapper().readValue(resultadoJson, ResultAI.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializando ResultAI", e);
        }
    }

    // Convierte un objeto ResultAI a JSON (String) para guardarlo en la BD.
    default String toResultadoJson(ResultAI resultAI){
        if(resultAI == null) return null;
        try {
            return new ObjectMapper().writeValueAsString(resultAI);
        } catch (Exception e) {
            throw new RuntimeException("Error serializando ResultAI", e);
        }
    }
}




