package com.app.veterinaria.infrastructure.persistence.jpa;

import com.app.veterinaria.infrastructure.persistence.entity.MascotaEntity;
import com.app.veterinaria.infrastructure.web.dto.details.MascotaWithDuenoDetails;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface MascotaJpaRepository extends ReactiveCrudRepository<MascotaEntity, UUID> {

    Mono<Boolean> existsByDuenoId(UUID duenoId);

    @Query("""
    SELECT
        m.id,
        m.nombre,
        m.especie,
        m.raza,
        concat(m.anios, '.', m.meses, ' meses') AS edad,
        m.sexo,
        m.temperamento,
        m.condicion_reproductiva AS condicionReproductiva,
        m.color,
        d.nombre AS dueno,
        m.identificador
    FROM mascota m
    LEFT JOIN dueno d ON m.dueno_id = d.id
    WHERE LOWER(m.nombre) LIKE LOWER(CONCAT('%', :term, '%'))
    ORDER BY m.nombre
    """)
    Flux<MascotaWithDuenoDetails> search(@Param("term") String term);

    @Query("""
    SELECT
        m.id,
        m.nombre,
        m.especie,
        m.raza,
        concat(m.anios, '.', m.meses, ' meses') AS edad,
        m.sexo,
        m.temperamento,
        m.condicion_reproductiva AS condicionReproductiva,
        m.color,
        d.nombre AS dueno,
        m.identificador
    FROM mascota m
    LEFT JOIN dueno d ON m.dueno_id = d.id
    WHERE (:especie IS NULL OR LOWER(m.especie) = LOWER(:especie))
      AND (:sexo IS NULL OR LOWER(m.sexo) = LOWER(:sexo))
      AND (:raza IS NULL OR LOWER(m.raza) = LOWER(:raza))
    ORDER BY m.nombre
    """)
    Flux<MascotaWithDuenoDetails> findByFilters(
            @Param("especie") String especie,
            @Param("sexo") String sexo,
            @Param("raza") String raza
    );

    @Query(value = """
        SELECT
            m.id,
            m.nombre,
            m.especie,
            m.raza,
            concat(m.anios, '.', m.meses, ' meses') AS edad,
            m.sexo,
            m.temperamento,
            m.condicion_reproductiva AS condicionreproductiva,
            m.color,
            d.nombre AS dueno,
            m.identificador
        FROM mascota m
        LEFT JOIN dueno d ON m.dueno_id = d.id
        ORDER BY m.fecha_creacion DESC
        LIMIT 20
        """)
    Flux<MascotaWithDuenoDetails> findAllWithDueno();

    @Query("""
    SELECT
            m.id,
            m.nombre,
            m.especie,
            m.raza,
            concat(m.anios, '.', m.meses, ' meses') AS edad,
            m.sexo,
            m.temperamento,
            m.condicion_reproductiva AS condicionReproductiva,
            m.color,
            d.nombre AS dueno,
            m.identificador
        FROM mascota m
        LEFT JOIN dueno d ON m.dueno_id = d.id
        WHERE m.id = :mascotaId
        ORDER BY m.nombre;
    """)
    Mono<MascotaWithDuenoDetails> findByIdDetails(@Param("mascotaId") UUID mascotaId);

    Flux<MascotaEntity> findByDuenoId(UUID duenoId);

}
