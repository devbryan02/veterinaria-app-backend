package com.app.veterinaria.application.mapper.request;

import com.app.veterinaria.domain.model.Imagen;
import com.app.veterinaria.domain.valueobject.ImagenDataCreate;
import com.app.veterinaria.infrastructure.web.dto.request.ImagenNewRequest;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ImagenRequestMapper {

    public Imagen toDomain(ImagenNewRequest request, String url, String publicId) {
        var data = new ImagenDataCreate(
                UUID.fromString(request.mascotaId()),
                url,
                request.descripcion(),
                publicId
        );
        return Imagen.nuevo(data);
    }

}
