package com.app.veterinaria.infrastructure.persistence.adapter.command;

import com.app.veterinaria.domain.model.Imagen;
import com.app.veterinaria.domain.repository.ImagenRepository;
import com.app.veterinaria.infrastructure.mapper.ImagenMapper;
import com.app.veterinaria.infrastructure.persistence.adapter.r2dbc.ImagenR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ImagenRepositoryImpl implements ImagenRepository {

    private final ImagenR2dbcRepository r2dbcRepository;
    private final ImagenMapper imagenMapper;

    @Override
    public Mono<Imagen> save(Imagen imagen) {
        return Mono.fromSupplier(() -> imagenMapper.toEntity(imagen))
                .flatMap(r2dbcRepository::save)
                .map(imagenMapper::toDomain);
    }

    @Override
    public Flux<Imagen> findAll() {
        return r2dbcRepository.findAll().map(imagenMapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByMascotaId(UUID mascotaId) {
        return r2dbcRepository.existsByMascotaId(mascotaId);
    }

    @Override
    public Flux<Imagen> findByMascotaId(UUID mascotaId) {
        return r2dbcRepository.findByMascotaId(mascotaId).map(imagenMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteByMascotaId(UUID id) {
        return r2dbcRepository.deleteAllByMascotaId(id);
    }

    @Override
    public Mono<Imagen> findById(UUID id) {
        return r2dbcRepository.findById(id).map(imagenMapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return r2dbcRepository.deleteById(id);
    }
}
