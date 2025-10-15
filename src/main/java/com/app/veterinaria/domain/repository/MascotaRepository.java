package com.app.veterinaria.domain.repository;

import com.app.veterinaria.domain.model.Mascota;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface MascotaRepository {

    Mono<Mascota> save(Mascota mascota);
    Flux<Mascota> findAll();
    Mono<Boolean> existsById(UUID mascotaId);

}
