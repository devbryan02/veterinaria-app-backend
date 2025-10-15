package com.app.veterinaria.infrastructure.persistence.jpa;

import com.app.veterinaria.infrastructure.persistence.entity.MascotaEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface MascotaJpaRepository extends ReactiveCrudRepository<MascotaEntity, UUID> {
}
