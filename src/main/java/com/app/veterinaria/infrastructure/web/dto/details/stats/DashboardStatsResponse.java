package com.app.veterinaria.infrastructure.web.dto.details.stats;

import java.util.List;

public record DashboardStatsResponse(
        int totalDuenos,
        int totalMascotas,
        int totalVacunas,
        MascotasPorEspecie mascotasPorEspecie,
        List<VacunasPorMes> vacunasPorMes,
        List<MascotasRegistradasPorAnio> mascotasRegistradasPorAnio
) {}
