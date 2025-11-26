package com.app.veterinaria.infrastructure.web.controller.veterinaria;

import com.app.veterinaria.application.service.stats.DashboardStatsService;
import com.app.veterinaria.infrastructure.web.dto.details.stats.DashboardStatsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/veterinaria/stats")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardStatsService dashboardStatsService;

    @GetMapping("/overview")
    public Mono<DashboardStatsResponse> stats() {
        return dashboardStatsService.obtenerEstadisticas();
    }
}

