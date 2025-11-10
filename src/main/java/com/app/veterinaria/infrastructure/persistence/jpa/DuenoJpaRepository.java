package com.app.veterinaria.infrastructure.persistence.jpa;

import com.app.veterinaria.infrastructure.persistence.entity.DuenoEntity;
import com.app.veterinaria.infrastructure.web.dto.details.DuenoWithCantMascotaDetails;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface DuenoJpaRepository extends ReactiveCrudRepository<DuenoEntity, UUID> {

    Mono<Boolean> existsByDNI(String DNI);
    Mono<Boolean> existsByCorreo(String correo);
    Mono<Boolean> existsByTelefono(String telefono);

    @Query("""
    SELECT
        d.id,
        d.nombre,
        d.dni,
        d.direccion,
        d.telefono,
        d.correo,
        COUNT(m.id) AS cantidadMascotas
    FROM dueno d
    LEFT JOIN mascota m ON d.id = m.dueno_id
    GROUP BY d.id, d.nombre, d.dni, d.direccion, d.telefono, d.correo
    ORDER BY d.nombre
    LIMIT 10;
    """)
    Flux<DuenoWithCantMascotaDetails> findAllWithLimit();

    @Query(value = """
        SELECT
            d.id AS id,
            d.nombre AS nombre,
            d.dni AS dni,
            d.direccion AS direccion,
            d.telefono AS telefono,
            d.correo AS correo,
            COUNT(m.id) AS "cantidadMascotas"
        FROM dueno d
        LEFT JOIN mascota m ON d.id = m.dueno_id
        WHERE d.id = :duenoId
        GROUP BY d.id, d.nombre, d.dni, d.direccion, d.telefono, d.correo
        """)
    Mono<DuenoWithCantMascotaDetails> findByIdDueno(@Param("duenoId") UUID duenoId);

    @Query(value = """
        SELECT
            d.id AS id,
            d.nombre AS nombre,
            d.dni AS dni,
            d.direccion AS direccion,
            d.telefono AS telefono,
            d.correo AS correo,
            COUNT(m.id) AS cantidadmascotas
        FROM dueno d
        LEFT JOIN mascota m ON d.id = m.dueno_id
        WHERE LOWER(d.nombre) LIKE LOWER(CONCAT('%', :term, '%'))
           OR LOWER(d.dni) LIKE LOWER(CONCAT('%', :term, '%'))
        GROUP BY d.id, d.nombre, d.dni, d.direccion, d.telefono, d.correo
        ORDER BY d.nombre
        """)
    Flux<DuenoWithCantMascotaDetails> searchByTerm(@Param("term") String term);

}
