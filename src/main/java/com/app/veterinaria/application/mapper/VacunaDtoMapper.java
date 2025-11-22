package com.app.veterinaria.application.mapper;

import com.app.veterinaria.domain.model.Mascota;
import com.app.veterinaria.domain.model.Vacuna;
import com.app.veterinaria.infrastructure.web.dto.request.VacunaNewRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface VacunaDtoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "mascota", expression = "java(mapMascota(request.mascotaId()))")
    @Mapping(target = "fechaAplicacion", expression = "java(toLocalDate(request.fechaAplicacion()))")
    Vacuna toDomain(VacunaNewRequest request);

    //metodo auxiliar para crear una mascota a partir de su ID
    default Mascota mapMascota(String mascotaId){
        if(mascotaId == null || mascotaId.isBlank()) return null;
        Mascota mascota = new Mascota();
        mascota.setId(UUID.fromString(mascotaId));
        return mascota;
    }

    //convetir string a Localdate
    default LocalDate toLocalDate(String fechaAplicacion){
        if(fechaAplicacion == null || fechaAplicacion.isBlank()) return null;
        return LocalDate.parse(fechaAplicacion);
    }


}
