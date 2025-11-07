package com.app.veterinaria.infrastructure.persistence.jpa;

import com.app.veterinaria.infrastructure.persistence.entity.MascotaEntity;
import com.app.veterinaria.infrastructure.web.dto.details.MascotaWithDuenoDetails;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface MascotaJpaRepository extends ReactiveCrudRepository<MascotaEntity, UUID> {

    Flux<MascotaEntity> findByDuenoId(UUID duenoId);
    Mono<Boolean> existsByDuenoId(UUID duenoId);

    @Query("SELECT * FROM mascota WHERE dueno_id IN (:duenoIds)")
    Flux<MascotaEntity> findByDuenoIdIn(@Param("duenoIds") List<UUID> duenoIds);

    @Query("SELECT * FROM mascota ORDER BY nombre LIMIT :limit")
    Flux<MascotaEntity> findAllWithLimit(int limit);

    @Query("""
    SELECT
        m.id,
        m.nombre,
        m.especie,
        m.raza,
        m.edad,
        m.sexo,
        m.temperamento,
        m.condicion_reproductiva AS condicionReproductiva,
        m.color,
        d.nombre AS dueno
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
            m.edad,
            m.sexo,
            m.temperamento,
            m.condicion_reproductiva AS condicionReproductiva,
            m.color,
            d.nombre AS dueno
            FROM mascota m
            LEFT JOIN dueno d on m.dueno_id = d.id
        WHERE (:especie IS NULL OR m.especie = :especie)
          AND (:sexo IS NULL OR m.sexo = :sexo)
          AND (:raza IS NULL OR m.raza = :raza)
        ORDER BY m.nombre
        """)
    Flux<MascotaWithDuenoDetails> findByFilters(@Param("especie") String especie,@Param("sexo") String sexo,@Param("raza") String raza);

    @Query(value = """
        SELECT
            m.id,
            m.nombre,
            m.especie,
            m.raza,
            m.edad,
            m.sexo,
            m.temperamento,
            m.condicion_reproductiva AS condicionreproductiva,
            m.color,
            d.nombre AS dueno
        FROM mascota m
        LEFT JOIN dueno d ON m.dueno_id = d.id
        ORDER BY m.nombre
        LIMIT 20
        """)
    Flux<MascotaWithDuenoDetails> findAllWithDueno();


    @Query("""
    SELECT
            m.id,
            m.nombre,
            m.especie,
            m.raza,
            m.edad,
            m.sexo,
            m.temperamento,
            m.condicion_reproductiva AS condicionReproductiva,
            m.color,
            d.nombre AS dueno
        FROM mascota m
        LEFT JOIN dueno d ON m.dueno_id = d.id
        WHERE m.id = :mascotaId
    """)
    Mono<MascotaWithDuenoDetails> findByIdDetails(@Param("mascotaId") UUID mascotaId);

}
