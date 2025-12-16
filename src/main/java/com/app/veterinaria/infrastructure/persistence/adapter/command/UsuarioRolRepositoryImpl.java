package com.app.veterinaria.infrastructure.persistence.adapter.command;

import com.app.veterinaria.domain.model.UsuarioRol;
import com.app.veterinaria.domain.repository.UsuarioRolRepository;
import com.app.veterinaria.infrastructure.mapper.UsuarioRolMapper;
import com.app.veterinaria.infrastructure.persistence.adapter.r2dbc.UsuarioRolR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UsuarioRolRepositoryImpl implements UsuarioRolRepository {

    private final UsuarioRolR2dbcRepository r2dbcRepository;
    private final UsuarioRolMapper mapper;

    @Override
    public Mono<UsuarioRol> save(UsuarioRol usuarioRol) {
        return Mono.fromSupplier(() -> mapper.toEntity(usuarioRol))
                .flatMap(r2dbcRepository::save)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteByUsuarioIdAndRolId(UUID usuarioId, UUID rolId) {
        return r2dbcRepository.deleteByUsuarioIdAndRolId(usuarioId, rolId);
    }

    @Override
    public Flux<UsuarioRol> findByUsuarioId(UUID usuarioId) {
        return r2dbcRepository.findByUsuarioId(usuarioId).map(mapper::toDomain);
    }

    @Override
    public Mono<Long> countByRolName(String rolName) {
        return r2dbcRepository.countByRolNombre(rolName);
    }

    @Override
    public Mono<Boolean> existsByUsuarioIdAndRolId(UUID usuarioId, UUID rolId) {
        return r2dbcRepository.existsByUsuarioIdAndRolId(usuarioId, rolId);
    }
}
