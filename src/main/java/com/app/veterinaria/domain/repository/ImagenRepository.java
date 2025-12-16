package com.app.veterinaria.domain.repository;

import com.app.veterinaria.domain.model.Imagen;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ImagenRepository {

    Mono<Imagen> save(Imagen imagen);
    Flux<Imagen> findAll();
    Mono<Boolean> existsByMascotaId(UUID mascotaId);
    Flux<Imagen> findByMascotaId(UUID mascotaId);
    Mono<Void> deleteByMascotaId(UUID mascotaId);
    Mono<Imagen> findById(UUID id);
    Mono<Void> deleteById(UUID id);

}
