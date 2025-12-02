package com.app.veterinaria.domain.repository;

import com.app.veterinaria.domain.model.Vacuna;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface VacunaRepository {

    Mono<Vacuna> save(Vacuna vacuna);
    Flux<Vacuna> findAll();
    Mono<Boolean> existsByMascotaId(UUID mascotaId);
    Flux<Vacuna> findByMascotaId(UUID mascotaId);
    Mono<Long> count();
    Mono<Long> countByMes(int mes);
    Mono<Long> countByAnio(int anio);
    Mono<Vacuna> findById(UUID id);
    Mono<Void> deleteById(UUID id);


}
