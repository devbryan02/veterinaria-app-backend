package com.app.veterinaria.application.repository;

import com.app.veterinaria.infrastructure.web.dto.details.VacunaWithMascotaDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

public interface VacunaQueryRepository {

    Mono<VacunaWithMascotaDetails> findById(UUID idVacuna);
    Flux<VacunaWithMascotaDetails> findAllVacunaWithMascota();
    Flux<VacunaWithMascotaDetails> findVacunasByDate(LocalDate startDate, LocalDate finalDate);
    Flux<VacunaWithMascotaDetails> filteByTipo(String tipo);

}
