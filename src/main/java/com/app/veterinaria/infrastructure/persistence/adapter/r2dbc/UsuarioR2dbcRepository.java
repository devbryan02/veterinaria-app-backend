package com.app.veterinaria.infrastructure.persistence.adapter.r2dbc;

import com.app.veterinaria.domain.model.Usuario;
import com.app.veterinaria.infrastructure.persistence.entity.UsuarioEntity;
import com.app.veterinaria.infrastructure.web.dto.details.DuenoWithCantMascotaDetails;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UsuarioR2dbcRepository extends R2dbcRepository<UsuarioEntity, UUID> {

    Mono<Boolean> existsByCorreo(String correo);
    Mono<Boolean> existsByDni(String dni);
    Mono<Boolean> existsByTelefono(String telefono);

    // Buscar usuario por correo
    Mono<UsuarioEntity> findByCorreo(String correo);

    @Query("""
    SELECT
        u.id,
        u.nombre,
        u.dni,
        u.direccion,
        u.telefono,
        u.correo,
        COUNT(m.id) AS cantidadMascotas
    FROM usuario u
    LEFT JOIN mascota m ON u.id = m.usuario_id
    LEFT JOIN usuario_rol ur ON u.id = ur.usuario_id
    LEFT JOIN rol r ON ur.rol_id = r.id
    WHERE r.nombre = 'DUENO'
    GROUP BY u.id, u.nombre, u.dni, u.direccion, u.telefono, u.correo
    ORDER BY u.nombre
    LIMIT 10
    """)
    Flux<DuenoWithCantMascotaDetails> findAllDuenosWithLimit();

    @Query(value = """
        SELECT
            u.id AS id,
            u.nombre AS nombre,
            u.dni AS dni,
            u.direccion AS direccion,
            u.telefono AS telefono,
            u.correo AS correo,
            COUNT(m.id) AS "cantidadMascotas"
        FROM usuario u
        LEFT JOIN mascota m ON u.id = m.usuario_id
        LEFT JOIN usuario_rol ur ON u.id = ur.usuario_id
        LEFT JOIN rol r ON ur.rol_id = r.id
        WHERE u.id = :usuarioId AND r.nombre = 'DUENO'
        GROUP BY u.id, u.nombre, u.dni, u.direccion, u.telefono, u.correo
        """)
    Mono<DuenoWithCantMascotaDetails> findByUsuarioDuenoId(@Param("usuarioId") UUID usuarioId);

    @Query(value = """
        SELECT
            u.id AS id,
            u.nombre AS nombre,
            u.dni AS dni,
            u.direccion AS direccion,
            u.telefono AS telefono,
            u.correo AS correo,
            COUNT(m.id) AS cantidadmascotas
        FROM usuario u
        LEFT JOIN mascota m ON u.id = m.usuario_id
        LEFT JOIN usuario_rol ur ON u.id = ur.usuario_id
        LEFT JOIN rol r ON ur.rol_id = r.id
        WHERE r.nombre = 'DUENO'
          AND (LOWER(u.nombre) LIKE LOWER(CONCAT('%', :term, '%'))
           OR LOWER(u.dni) LIKE LOWER(CONCAT('%', :term, '%')))
        GROUP BY u.id, u.nombre, u.dni, u.direccion, u.telefono, u.correo
        ORDER BY u.nombre
        """)
    Flux<DuenoWithCantMascotaDetails> searchByTerm(@Param("term") String term);
}