package com.app.veterinaria.infrastructure.persistence.jpa;

import com.app.veterinaria.infrastructure.persistence.entity.AdminEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface AdminJpaRepository extends ReactiveCrudRepository<AdminEntity, UUID> {

    Mono<AdminEntity> findByCorreo(String correo);
}
