package com.app.veterinaria.application.service.stats;

import com.app.veterinaria.domain.repository.DuenoRepository;
import com.app.veterinaria.domain.repository.MascotaRepository;
import com.app.veterinaria.domain.repository.VacunaRepository;
import com.app.veterinaria.infrastructure.web.dto.details.stats.DashboardStatsResponse;
import com.app.veterinaria.infrastructure.web.dto.details.stats.MascotasPorEspecie;
import com.app.veterinaria.infrastructure.web.dto.details.stats.MascotasRegistradasPorAnio;
import com.app.veterinaria.infrastructure.web.dto.details.stats.VacunasPorMes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Month;
import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardStatsService {

    private final DuenoRepository duenoRepository;
    private final MascotaRepository mascotaRepository;
    private final VacunaRepository vacunaRepository;

    public Mono<DashboardStatsResponse> obtenerEstadisticas() {

        // -------------------------
        // TOTALES
        // -------------------------
        Mono<Integer> totalDuenosMono = duenoRepository.count().map(Long::intValue);
        Mono<Integer> totalMascotasMono = mascotaRepository.count().map(Long::intValue);
        Mono<Integer> totalVacunasMono = vacunaRepository.count().map(Long::intValue);

        // -------------------------
        // MASCOTAS POR ESPECIE
        // -------------------------
        Mono<MascotasPorEspecie> mascotasPorEspecieMono = obtenerMascotasPorEspecie()
                .doOnNext(m -> log.info("Contando mascotas po especie"));

        // -------------------------
        // VACUNAS POR MES (12 meses)
        // -------------------------
        Mono<List<VacunasPorMes>> vacunasPorMesMono = obtenerVacunasPorMes()
                .doOnNext(m -> log.info("Contando Vacunas por mes"));

        // -------------------------
        // MASCOTAS POR AÑO
        // -------------------------
        Mono<List<MascotasRegistradasPorAnio>> mascotasPorAnioMono = obtenerMascotasPorAnio()
                .doOnNext(m -> log.info("Contando mascotas por anio"));

        return Mono.zip(
                totalDuenosMono,
                totalMascotasMono,
                totalVacunasMono,
                mascotasPorEspecieMono,
                vacunasPorMesMono,
                mascotasPorAnioMono
        ).map(tuple -> new DashboardStatsResponse(
                tuple.getT1(),
                tuple.getT2(),
                tuple.getT3(),
                tuple.getT4(),
                tuple.getT5(),
                tuple.getT6()
        ));
    }

    // --------------------------------------------------------------------
    // Mascotas por especie usando countByEspecie()
    // --------------------------------------------------------------------
    private Mono<MascotasPorEspecie> obtenerMascotasPorEspecie() {
        Mono<Integer> perros = mascotaRepository.countByEspecie("Perro").map(Long::intValue);
        Mono<Integer> gatos = mascotaRepository.countByEspecie("Gato").map(Long::intValue);
        Mono<Integer> conejos = mascotaRepository.countByEspecie("Conejo").map(Long::intValue);

        return Mono.zip(perros, gatos, conejos)
                .map(t -> new MascotasPorEspecie(
                        t.getT1(),
                        t.getT2(),
                        t.getT3()
                ));
    }

    // --------------------------------------------------------------------
    // Vacunas por mes (1–12) usando countByMes()
    // --------------------------------------------------------------------
    private Mono<List<VacunasPorMes>> obtenerVacunasPorMes() {
        return Flux.range(1, 12)
                .flatMap(mes ->
                        vacunaRepository.countByMes(mes)
                                .map(total -> new VacunasPorMes(
                                        Month.of(mes).name(),
                                        total.intValue()
                                ))
                )
                .collectList();
    }

    // --------------------------------------------------------------------
    // Mascotas registradas por año usando countByAnio()
    // --------------------------------------------------------------------
    private Mono<List<MascotasRegistradasPorAnio>> obtenerMascotasPorAnio() {

        int currentYear = Year.now().getValue();
        int startYear = currentYear - 5; // últimos 5 años

        return Flux.range(startYear, currentYear - startYear + 1)
                .flatMap(anio ->
                        mascotaRepository.countByAnio(anio)
                                .map(total -> new MascotasRegistradasPorAnio(anio, total.intValue()))
                )
                .sort((a, b) -> b.anio() - a.anio()) // descendente
                .collectList();
    }
}
