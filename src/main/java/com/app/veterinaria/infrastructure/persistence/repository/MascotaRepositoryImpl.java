package com.app.veterinaria.infrastructure.persistence.repository;

import com.app.veterinaria.domain.model.Mascota;
import com.app.veterinaria.domain.repository.MascotaRepository;
import com.app.veterinaria.infrastructure.mapper.MascotaMapper;
import com.app.veterinaria.infrastructure.persistence.entity.MascotaEntity;
import com.app.veterinaria.infrastructure.persistence.jpa.MascotaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MascotaRepositoryImpl implements MascotaRepository {

    private final MascotaMapper mascotaMapper;
    private final MascotaJpaRepository mascotaJpaRepository;

    @Override
    public Mono<Mascota> save(Mascota mascota) {
        MascotaEntity entity = mascotaMapper.toEntity(mascota);
        return mascotaJpaRepository.save(entity).map(mascotaMapper::toDomain);
    }

    @Override
    public Flux<Mascota> findAll() {
        return mascotaJpaRepository.findAll().map(mascotaMapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsById(UUID mascotaId) {
        return mascotaJpaRepository.existsById(mascotaId);
    }
}
