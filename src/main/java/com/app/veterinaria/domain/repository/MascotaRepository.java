package com.app.veterinaria.domain.repository;

import com.app.veterinaria.domain.model.Mascota;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface MascotaRepository {

    Mono<Mascota> save(Mascota mascota);
    Flux<Mascota> findAll();
    Mono<Boolean> existsById(UUID mascotaId);
    Mono<Void> deleteById(UUID mascotaId);
    Mono<Void> update(Mascota mascota);
    Mono<Mascota> findById(UUID mascotaId);
    Flux<Mascota> findByDuenoId(UUID duenoId);
    Mono<Boolean> existsByDuenoId(UUID duenoId);

}
