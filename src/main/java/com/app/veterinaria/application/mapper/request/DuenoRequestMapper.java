package com.app.veterinaria.application.mapper.request;

import com.app.veterinaria.domain.model.Usuario;
import com.app.veterinaria.domain.valueobject.DuenoDataCreate;
import com.app.veterinaria.domain.valueobject.DuenoDataUpdate;
import com.app.veterinaria.infrastructure.web.dto.request.DuenoNewRequest;
import com.app.veterinaria.infrastructure.web.dto.request.DuenoUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class DuenoRequestMapper {

    public Usuario toDomain(DuenoNewRequest request){
        var data = new DuenoDataCreate(
                request.nombre(),
                request.correo(),
                request.password(),
                request.telefono(),
                request.dni(),
                request.direccion(),
                request.latitud(),
                request.longitud()
        );

        return Usuario.nuevoDueno(data);
    }

    public Usuario toUpdate(DuenoUpdateRequest request, Usuario actual){
        var data = new DuenoDataUpdate(
                request.nombre(),
                request.correo(),
                request.telefono(),
                request.dni(),
                request.direccion()
        );
        return actual.actualizar(data);
    }

}
