package com.app.veterinaria.domain.repository;

import com.app.veterinaria.domain.model.Rol;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface RolRepository {

    Mono<Rol> save(Rol rol);
    Mono<Rol> findById(UUID id);
    Mono<Rol> findByNombre(String nombre);
    Flux<Rol> findAll();
    Flux<Rol> findRolesByUsuarioId(UUID usuarioId);

}
