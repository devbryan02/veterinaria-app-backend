package com.app.veterinaria.infrastructure.persistence.jpa;

import com.app.veterinaria.infrastructure.persistence.entity.DuenoEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface DuenoJpaRepository extends ReactiveCrudRepository<DuenoEntity, UUID> {

    Mono<Boolean> existsByDNI(String DNI);
    Mono<Boolean> existsByCorreo(String correo);
    Mono<Boolean> existsByTelefono(String telefono);
}
