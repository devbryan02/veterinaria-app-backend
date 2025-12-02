package com.app.veterinaria.application.mapper.request;

import com.app.veterinaria.domain.model.Vacuna;
import com.app.veterinaria.domain.valueobject.VacunaDataCreate;
import com.app.veterinaria.domain.valueobject.VacunaDataUpdate;
import com.app.veterinaria.infrastructure.web.dto.request.VacunaNewRequest;
import com.app.veterinaria.infrastructure.web.dto.request.VacunaUpdateRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class VacunaRequestMapper {

    public Vacuna toDomain(VacunaNewRequest request){
        var data = new VacunaDataCreate(
                UUID.fromString(request.mascotaId()),
                request.tipo(),
                LocalDate.parse(request.fechaAplicacion()),
                request.mesesVigencia()
        );
        return Vacuna.nuevo(data);
    }

    public Vacuna toUpdate(VacunaUpdateRequest request, Vacuna actual){
        var datos = new VacunaDataUpdate(
                request.tipo(),
                LocalDate.parse(request.fechaAplicacion()),
                request.mesesVigencia()
        );
        return actual.actualizar(datos);
    }
}
