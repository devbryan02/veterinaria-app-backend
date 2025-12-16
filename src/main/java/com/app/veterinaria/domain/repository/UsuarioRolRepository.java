package com.app.veterinaria.domain.repository;

import com.app.veterinaria.domain.model.UsuarioRol;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UsuarioRolRepository {

    Mono<UsuarioRol> save(UsuarioRol usuarioRol);
    Mono<Void> deleteByUsuarioIdAndRolId(UUID usuarioId, UUID rolId);
    Flux<UsuarioRol> findByUsuarioId(UUID usuarioId);
    Mono<Long> countByRolName(String rolName);
    Mono<Boolean> existsByUsuarioIdAndRolId(UUID usuarioId, UUID rolId);
}
