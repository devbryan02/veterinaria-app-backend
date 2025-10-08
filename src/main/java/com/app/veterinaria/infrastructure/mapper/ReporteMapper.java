package com.app.veterinaria.infrastructure.mapper;

import com.app.veterinaria.domain.model.Admin;
import com.app.veterinaria.domain.model.Reporte;
import com.app.veterinaria.domain.record.DataReport;
import com.app.veterinaria.infrastructure.persistence.entity.ReporteEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface ReporteMapper {

    @Mapping(target = "admin", expression = "java(toAdmin(entity.getAdminId()))")
    Reporte toDomain(ReporteEntity entity);

    @Mapping(target ="adminId", source = "admin.id")
    ReporteEntity toEntity(Reporte domain);

    default Admin toAdmin(UUID adminId){
        if(adminId == null) return null;
        Admin admin = new Admin();
        admin.setId(adminId);
        return admin;
    }

    default UUID toAdmin(Admin admin){
        return admin != null ? admin.getId() : null;
    }

    //metodos para transformar recod a Json
    default DataReport toDataReport(String dataReportJson){
        if(dataReportJson == null) return null;
        try{
            return new ObjectMapper().readValue(dataReportJson, DataReport.class);
        }catch (Exception e){
            throw new RuntimeException("Error serializando dataReportJson");
        }
    }

    default String toDataReportJson(DataReport dataReport){
        if(dataReport == null) return null;
        try{
            return new ObjectMapper().writeValueAsString(dataReport);
        }catch (Exception e){
            throw new RuntimeException("Error serializando dataReportJson");
        }
    }
}
