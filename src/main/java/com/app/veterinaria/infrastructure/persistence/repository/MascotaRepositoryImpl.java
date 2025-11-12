package com.app.veterinaria.infrastructure.persistence.repository;

import com.app.veterinaria.domain.model.Mascota;
import com.app.veterinaria.domain.repository.MascotaRepository;
import com.app.veterinaria.infrastructure.mapper.MascotaMapper;
import com.app.veterinaria.infrastructure.persistence.entity.MascotaEntity;
import com.app.veterinaria.infrastructure.persistence.jpa.MascotaJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@Slf4j
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
    public Flux<Mascota> findAll(int limit) {
        return mascotaJpaRepository.findAllWithLimit(limit).map(mascotaMapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsById(UUID mascotaId) {
        return mascotaJpaRepository.existsById(mascotaId);
    }

    @Override
    public Mono<Void> deleteById(UUID mascotaId) {
        return mascotaJpaRepository.deleteById(mascotaId);
    }

    @Override
    public Mono<Void> update(Mascota mascota) {
        return mascotaJpaRepository.save(mascotaMapper.toEntity(mascota)).then();
    }

    @Override
    public Mono<Mascota> findById(UUID mascotaId) {
        return mascotaJpaRepository.findById(mascotaId).map(mascotaMapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByDuenoId(UUID duenoId) {
        return mascotaJpaRepository.existsByDuenoId(duenoId);
    }
}
