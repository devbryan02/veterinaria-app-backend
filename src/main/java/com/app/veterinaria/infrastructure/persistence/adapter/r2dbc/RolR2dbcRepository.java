package com.app.veterinaria.infrastructure.persistence.adapter.r2dbc;

import com.app.veterinaria.infrastructure.persistence.entity.RolEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface RolR2dbcRepository extends R2dbcRepository<RolEntity, UUID> {

    Mono<RolEntity> findByNombre(String nombre);

    @Query("""
        SELECT r.* FROM rol r
        INNER JOIN usuario_rol ur ON r.id = ur.rol_id
        WHERE ur.usuario_id = :usuarioId
        """)
    Flux<RolEntity> findRolesByUsuarioId(UUID usuarioId);
}
