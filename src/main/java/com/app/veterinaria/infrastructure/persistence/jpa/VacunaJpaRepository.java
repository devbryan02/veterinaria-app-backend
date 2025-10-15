package com.app.veterinaria.infrastructure.persistence.jpa;

import com.app.veterinaria.infrastructure.persistence.entity.VacunaEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface VacunaJpaRepository extends ReactiveCrudRepository<VacunaEntity, UUID> {
}
