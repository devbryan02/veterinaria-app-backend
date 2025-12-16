package com.app.veterinaria.domain.model;


import com.app.veterinaria.domain.valueobject.ImagenDataCreate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record Imagen(
        UUID id,
        UUID mascotaId,
        String url,
        String descripcion,
        LocalDate fechaSubida,
        LocalDateTime createdAt,
        String publicId
) {

    public static Imagen nuevo(ImagenDataCreate data){
        return new Imagen(
                null,
                data.mascotaId(),
                data.url(),
                data.descripcion(),
                LocalDate.now(),
                LocalDateTime.now(),
                data.publicId()
        );
    }
}