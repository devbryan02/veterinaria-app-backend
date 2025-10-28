package com.app.veterinaria.infrastructure.persistence.jpa;

import com.app.veterinaria.infrastructure.persistence.entity.MascotaEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface MascotaJpaRepository extends ReactiveCrudRepository<MascotaEntity, UUID> {

    Flux<MascotaEntity> findByDuenoId(UUID duenoId);
    Mono<Boolean> existsByDuenoId(UUID duenoId);

}
