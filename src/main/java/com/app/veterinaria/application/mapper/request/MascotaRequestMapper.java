package com.app.veterinaria.application.mapper.request;

import com.app.veterinaria.domain.emuns.EstadoMascota;
import com.app.veterinaria.domain.emuns.SexoEnum;
import com.app.veterinaria.domain.model.Mascota;
import com.app.veterinaria.domain.valueobject.MascotaDataCreate;
import com.app.veterinaria.domain.valueobject.MascotaDataUpdate;
import com.app.veterinaria.infrastructure.web.dto.request.MascotaNewRequest;
import com.app.veterinaria.infrastructure.web.dto.request.MascotaUpdateRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MascotaRequestMapper {

    public Mascota toDomain(MascotaNewRequest request) {
        var data =  new MascotaDataCreate(
                UUID.fromString(request.usuarioId()),
                request.nombre(),
                request.especie(),
                request.raza(),
                SexoEnum.valueOf(request.sexo()),
                request.temperamento(),
                request.condicionReproductiva(),
                request.color(),
                request.anios(),
                request.meses()
        );
        return Mascota.nuevo(data);
    }

    public Mascota toUpdate(MascotaUpdateRequest request, Mascota actual){
        var data = new MascotaDataUpdate(
                request.nombre(),
                request.especie(),
                request.raza(),
                SexoEnum.valueOf(request.sexo()),
                request.temperamento(),
                request.condicionReproductiva(),
                request.color(),
                request.anios(),
                request.meses(),
                EstadoMascota.valueOf(request.estado())

        );
        return actual.actualizar(data);
    }
}
