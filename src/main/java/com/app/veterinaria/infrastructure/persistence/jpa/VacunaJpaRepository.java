package com.app.veterinaria.infrastructure.persistence.jpa;

import com.app.veterinaria.infrastructure.persistence.entity.VacunaEntity;
import com.app.veterinaria.infrastructure.web.dto.details.VacunaWithMascotaDetails;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

public interface VacunaJpaRepository extends ReactiveCrudRepository<VacunaEntity, UUID> {

    Mono<Boolean> existsByMascotaId(UUID mascocotaId);

    @Query("""
    SELECT
        v.id as id,
        v.tipo as tipo,
        v.fecha_aplicacion as fechaaplicacion,
        m.nombre as mascota,
        v.meses_vigencia as mesesvigencia,
        v.fecha_vencimiento as fechavencimiento,
        v.proxima_dosis as proximadosis
    FROM vacuna v
    LEFT JOIN mascota m on m.id = v.mascota_id
    ORDER BY v.fecha_aplicacion DESC
    LIMIT 10
    """)
    Flux<VacunaWithMascotaDetails> findAllVacunas();

    @Query("""
    SELECT
        v.id as id,
        v.tipo as tipo,
        v.fecha_aplicacion as fechaaplicacion,
        m.nombre as mascota,
        v.meses_vigencia as mesesvigencia,
        v.fecha_vencimiento as fechavencimiento,
        v.proxima_dosis as proximadosis
    FROM vacuna v
    LEFT JOIN mascota m on m.id = v.mascota_id
    WHERE v.fecha_aplicacion BETWEEN :startDate AND :endDate
    ORDER BY v.fecha_aplicacion DESC
    LIMIT 10;
    """)
    Flux<VacunaWithMascotaDetails> findBydate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("""
    SELECT
        v.id as id,
        v.tipo as tipo,
        v.fecha_aplicacion as fechaaplicacion,
        m.nombre as mascota,
        v.meses_vigencia as mesesvigencia,
        v.fecha_vencimiento as fechavencimiento,
        v.proxima_dosis as proximadosis
    FROM vacuna v
    LEFT JOIN mascota m on m.id = v.mascota_id
    WHERE LOWER(v.tipo) = LOWER(:tipo)
    LIMIT 10;
    """)
    Flux<VacunaWithMascotaDetails> filterByTipo(@Param("tipo") String tipo);


    @Query("""
    SELECT
        v.id as id,
        v.tipo as tipo,
        v.fecha_aplicacion as fechaaplicacion,
        m.nombre as mascota,
        v.meses_vigencia as mesesvigencia,
        v.fecha_vencimiento as fechavencimiento,
        v.proxima_dosis as proximadosis
    FROM vacuna v
    LEFT JOIN mascota m on m.id = v.mascota_id
    WHERE v.id = :idVacuna;
    """)
    Mono<VacunaWithMascotaDetails> findByIdVacuna(@Param("idVacuna") UUID idVacuna);

    Flux<VacunaEntity> findByMascotaId(UUID mascotaId);

}
