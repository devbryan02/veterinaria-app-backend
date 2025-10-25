package com.app.veterinaria.infrastructure.persistence.jpa;

import com.app.veterinaria.infrastructure.persistence.entity.ImagenEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ImagenJpaRepository extends ReactiveCrudRepository<ImagenEntity, UUID> {

    Mono<Boolean> existsByMascotaId(UUID mascotaId);

}
