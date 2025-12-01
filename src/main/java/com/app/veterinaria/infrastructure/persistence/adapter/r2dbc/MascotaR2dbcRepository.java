package com.app.veterinaria.infrastructure.persistence.adapter.r2dbc;

import com.app.veterinaria.infrastructure.persistence.entity.MascotaEntity;
import com.app.veterinaria.infrastructure.web.dto.details.MascotaWithDuenoDetails;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface MascotaR2dbcRepository extends R2dbcRepository<MascotaEntity, UUID> {

    Mono<Boolean> existsByUsuarioId(UUID usuarioId);

    @Query("""
    SELECT
        m.id,
        m.nombre,
        m.especie,
        m.raza,
        concat(m.anios, ' años ', m.meses, ' meses') AS edad,
        m.sexo,
        m.temperamento,
        m.condicion_reproductiva AS condicionReproductiva,
        m.color,
        u.nombre AS dueno,
        m.identificador
    FROM mascota m
    LEFT JOIN usuario u ON m.usuario_id = u.id
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
        concat(m.anios, ' años ', m.meses, ' meses') AS edad,
        m.sexo,
        m.temperamento,
        m.condicion_reproductiva AS condicionReproductiva,
        m.color,
        u.nombre AS dueno,
        m.identificador
    FROM mascota m
    LEFT JOIN usuario u ON m.usuario_id = u.id
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
            concat(m.anios, ' años ', m.meses, ' meses') AS edad,
            m.sexo,
            m.temperamento,
            m.condicion_reproductiva AS condicionreproductiva,
            m.color,
            u.nombre AS dueno,
            m.identificador
        FROM mascota m
        LEFT JOIN usuario u ON m.usuario_id = u.id
        ORDER BY m.created_at DESC
        LIMIT 20
        """)
    Flux<MascotaWithDuenoDetails> findAllWithDueno();

    @Query("""
    SELECT
            m.id,
            m.nombre,
            m.especie,
            m.raza,
            concat(m.anios, ' años ', m.meses, ' meses') AS edad,
            m.sexo,
            m.temperamento,
            m.condicion_reproductiva AS condicionReproductiva,
            m.color,
            u.nombre AS dueno,
            m.identificador
        FROM mascota m
        LEFT JOIN usuario u ON m.usuario_id = u.id
        WHERE m.id = :mascotaId
        ORDER BY m.nombre;
    """)
    Mono<MascotaWithDuenoDetails> findByIdDetails(@Param("mascotaId") UUID mascotaId);

    Flux<MascotaEntity> findByUsuarioId(UUID duenoId);

    @Query("SELECT COUNT(*) FROM mascota WHERE especie = :especie")
    Mono<Long> countByEspecie(String especie);

    @Query("""
    SELECT COUNT(*)
    FROM mascota
    WHERE EXTRACT(YEAR FROM created_at) = :anio
    """)
    Mono<Long> countByAnio(int anio);


}
