package com.app.veterinaria.domain.repository;

import com.app.veterinaria.domain.model.Vacuna;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface VacunaRepository {

    Mono<Vacuna> save(Vacuna vacuna);
    Flux<Vacuna> findAll();

}
