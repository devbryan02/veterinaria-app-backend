package com.app.veterinaria.domain.model;

import com.app.veterinaria.domain.valueobject.VacunaDataCreate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record Vacuna(
        UUID id,
        UUID mascotaId,
        String tipo,
        LocalDate fechaAplicacion,
        Integer mesesVigencia,
        LocalDate fechaVencimiento,
        LocalDate proximaDosis,
        LocalDateTime createdAt
) {

    public static Vacuna nuevo(VacunaDataCreate data){

        LocalDate fechaVencimientoCalc = data.fechaAplicacion().plusMonths(data.mesesVigencia());
        LocalDate proximaDosisCalc = fechaVencimientoCalc.minusMonths(1);

        return new Vacuna(
                null,
                data.mascotaId(),
                data.tipo(),
                data.fechaAplicacion(),
                data.mesesVigencia(),
                fechaVencimientoCalc,
                proximaDosisCalc,
                LocalDateTime.now()
        );
    }
}