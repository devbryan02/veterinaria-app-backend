package com.app.veterinaria.infrastructure.persistence.repository;

import com.app.veterinaria.domain.model.Vacuna;
import com.app.veterinaria.domain.repository.VacunaRepository;
import com.app.veterinaria.infrastructure.mapper.VacunaMapper;
import com.app.veterinaria.infrastructure.persistence.entity.VacunaEntity;
import com.app.veterinaria.infrastructure.persistence.jpa.VacunaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class VacunaRepositoryImpl implements VacunaRepository {

    private final VacunaMapper  mapper;
    private final VacunaJpaRepository vacunaJpaRepository;
    private final VacunaMapper vacunaMapper;

    @Override
    public Mono<Vacuna> save(Vacuna vacuna) {
        VacunaEntity entity = mapper.toEntity(vacuna);
        return vacunaJpaRepository.save(entity).map(vacunaMapper::toDomain);
    }

    @Override
    public Flux<Vacuna> findAll() {
        return vacunaJpaRepository.findAll().map(vacunaMapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByMascotaId(UUID mascotaId) {
        return vacunaJpaRepository.existsByMascotaId(mascotaId);
    }
}
