package com.app.veterinaria.domain.record;

import java.time.LocalDateTime;
import java.util.List;

public record ResultAI(

        String idAnalisisExterno,
        LocalDateTime tiempoAnalisis,
        String diagnosticoPrincipal,
        String descripcionVisual,
        List<RazaDetectada> razasDetectadas,
        String estimacionPeso,
        List<String> anomaliasDetectadas,
        String recomendaciones
) {
    // subrecord para reza dectada
    public record RazaDetectada(
            String nombreRaza,
            Double probabilidad // Probabilidad de 0.0 a 1.0
    ) {}
}
