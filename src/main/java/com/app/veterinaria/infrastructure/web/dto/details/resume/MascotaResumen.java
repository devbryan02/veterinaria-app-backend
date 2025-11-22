package com.app.veterinaria.infrastructure.web.dto.details.resume;

import java.util.List;

public record MascotaResumen(
        Integer cantidadMascotas,
        List<MascotaDetalle> mascotasList
) { }
