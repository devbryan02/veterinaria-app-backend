package com.app.veterinaria.infrastructure.persistence.adapter.r2dbc;

import com.app.veterinaria.infrastructure.persistence.entity.AuditoriaEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.UUID;

public interface AuditoriaR2dbcRepository extends R2dbcRepository<AuditoriaEntity, UUID> {

    Flux<AuditoriaEntity> findByUsuarioId(UUID usuarioId);
    Flux<AuditoriaEntity> findByEntidad(String entidad);

    @Query("""
        SELECT * FROM auditoria
        WHERE created_at BETWEEN :inicio AND :fin
        ORDER BY created_at DESC
        """)
    Flux<AuditoriaEntity> findByFechaRange(LocalDateTime inicio, LocalDateTime fin);

}
