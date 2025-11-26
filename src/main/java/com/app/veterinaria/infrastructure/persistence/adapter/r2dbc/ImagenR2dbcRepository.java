package com.app.veterinaria.infrastructure.persistence.adapter.r2dbc;

import com.app.veterinaria.infrastructure.persistence.entity.ImagenEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface ImagenR2dbcRepository extends R2dbcRepository<ImagenEntity, UUID> {

    Mono<Boolean> existsByMascotaId(UUID mascotaId);

    Flux<ImagenEntity> findByMascotaId(UUID mascotaId);

}
