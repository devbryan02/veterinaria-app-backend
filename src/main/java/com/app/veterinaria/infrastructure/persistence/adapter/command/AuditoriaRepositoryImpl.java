package com.app.veterinaria.infrastructure.persistence.adapter.command;

import com.app.veterinaria.domain.model.Auditoria;
import com.app.veterinaria.domain.repository.AuditoriaRepository;
import com.app.veterinaria.infrastructure.mapper.AuditoriaMapper;
import com.app.veterinaria.infrastructure.persistence.adapter.r2dbc.AuditoriaR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AuditoriaRepositoryImpl implements AuditoriaRepository {

    private final AuditoriaR2dbcRepository r2dbcRepository;
    private final AuditoriaMapper mapper;

    @Override
    public Mono<Auditoria> save(Auditoria auditoria) {
        return Mono.fromSupplier(() -> mapper.toEntity(auditoria))
                .flatMap(r2dbcRepository::save)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Auditoria> findByUsuarioId(UUID usuarioId) {
        return r2dbcRepository.findByUsuarioId(usuarioId).map(mapper::toDomain);
    }

    @Override
    public Flux<Auditoria> findByEntidad(String entidad) {
        return r2dbcRepository.findByEntidad(entidad).map(mapper::toDomain);
    }

    @Override
    public Flux<Auditoria> findByFechaRange(LocalDateTime inicio, LocalDateTime fin) {
        return r2dbcRepository.findByFechaRange(inicio, fin).map(mapper::toDomain);
    }

    @Override
    public Flux<Auditoria> findAll() {
        return r2dbcRepository.findAll().map(mapper::toDomain);
    }
}
