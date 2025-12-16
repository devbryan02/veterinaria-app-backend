package com.app.veterinaria.domain.valueobject;

import java.util.UUID;

public record ImagenDataCreate(
    UUID mascotaId,
    String url,
    String descripcion,
    String publicId
) { }
