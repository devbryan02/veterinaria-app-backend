package com.app.veterinaria.infrastructure.persistence.repository;

import com.app.veterinaria.domain.model.Imagen;
import com.app.veterinaria.domain.repository.ImagenRepository;
import com.app.veterinaria.infrastructure.mapper.ImagenMapper;
import com.app.veterinaria.infrastructure.persistence.entity.ImagenEntity;
import com.app.veterinaria.infrastructure.persistence.jpa.ImagenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ImagenRepositoryImpl implements ImagenRepository {

    private final ImagenJpaRepository imagenJpaRepository;
    private final ImagenMapper imagenMapper;

    @Override
    public Mono<Imagen> save(Imagen imagen) {
        ImagenEntity entity = imagenMapper.toEntity(imagen);
        return imagenJpaRepository.save(entity).map(imagenMapper::toDomain);
    }

    @Override
    public Flux<Imagen> findAll() {
        return imagenJpaRepository.findAll().map(imagenMapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByMascotaId(UUID mascotaId) {
        return imagenJpaRepository.existsByMascotaId(mascotaId);
    }

    @Override
    public Flux<Imagen> findByMascotaId(UUID mascotaId) {
        return imagenJpaRepository.findByMascotaId(mascotaId).map(imagenMapper::toDomain);
    }
}
