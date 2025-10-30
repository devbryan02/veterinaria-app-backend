package com.app.veterinaria.infrastructure.persistence.jpa;

import com.app.veterinaria.infrastructure.persistence.entity.DuenoEntity;
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
    SELECT * FROM dueno
    WHERE LOWER(nombre) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
       OR LOWER(DNI) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
    """)
    Flux<DuenoEntity> search(@Param("searchTerm") String searchTerm);

}
