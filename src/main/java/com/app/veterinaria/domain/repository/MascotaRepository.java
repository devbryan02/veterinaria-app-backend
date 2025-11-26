package com.app.veterinaria.domain.repository;

import com.app.veterinaria.domain.model.Mascota;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface MascotaRepository {

    Mono<Mascota> save(Mascota mascota);
    Mono<Boolean> existsById(UUID mascotaId);
    Mono<Void> deleteById(UUID mascotaId);
    Mono<Mascota> findById(UUID mascotaId);
    Mono<Boolean> existsByUsuarioId(UUID usuarioId);
    Flux<Mascota> findByUsuarioId(UUID usuarioId);
    Flux<Mascota> findAll();
    Mono<Long> count();
    Mono<Long> countByEspecie(String especie);
    Mono<Long> countByAnio(int anio);

}
