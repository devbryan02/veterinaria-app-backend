package com.app.veterinaria.infrastructure.persistence.adapter.command;

import com.app.veterinaria.domain.model.Rol;
import com.app.veterinaria.domain.repository.RolRepository;
import com.app.veterinaria.infrastructure.mapper.RolMapper;
import com.app.veterinaria.infrastructure.persistence.adapter.r2dbc.RolR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class RolRepositoryImpl implements RolRepository {

    private final RolR2dbcRepository r2dbcRepository;
    private final RolMapper mapper;

    @Override
    public Mono<Rol> save(Rol rol) {
        return Mono.just(rol)
                .map(mapper::toEntity)
                .flatMap(r2dbcRepository::save)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Rol> findById(UUID id) {
        return r2dbcRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Mono<Rol> findByNombre(String nombre) {
        return r2dbcRepository.findByNombre(nombre).map(mapper::toDomain);
    }

    @Override
    public Flux<Rol> findAll() {
        return r2dbcRepository.findAll().map(mapper::toDomain);
    }

    @Override
    public Flux<Rol> findRolesByUsuarioId(UUID usuarioId) {
        return r2dbcRepository.findRolesByUsuarioId(usuarioId).map(mapper::toDomain);
    }
}
