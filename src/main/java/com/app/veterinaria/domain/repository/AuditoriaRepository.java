package com.app.veterinaria.domain.repository;

import com.app.veterinaria.domain.model.Auditoria;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

public interface AuditoriaRepository {

    Mono<Auditoria> save(Auditoria auditoria);
    Flux<Auditoria> findByUsuarioId(UUID usuarioId);
    Flux<Auditoria> findByEntidad(String entidad);
    Flux<Auditoria> findByFechaRange(LocalDateTime inicio, LocalDateTime fin);
    Flux<Auditoria> findAll();

}
