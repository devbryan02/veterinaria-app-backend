package com.app.veterinaria.infrastructure.persistence.adapter.r2dbc;

import com.app.veterinaria.infrastructure.persistence.entity.UsuarioRolEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UsuarioRolR2dbcRepository extends R2dbcRepository<UsuarioRolEntity, UUID> {

    Flux<UsuarioRolEntity> findByUsuarioId(UUID usuarioId);
    Mono<Void> deleteByUsuarioIdAndRolId(UUID usuarioId, UUID rolId);
    Mono<Boolean> existsByUsuarioIdAndRolId(UUID usuarioId, UUID rolId);
}
