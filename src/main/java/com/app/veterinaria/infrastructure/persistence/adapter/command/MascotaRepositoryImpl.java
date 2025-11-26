package com.app.veterinaria.infrastructure.persistence.adapter.command;

import com.app.veterinaria.domain.model.Mascota;
import com.app.veterinaria.domain.repository.MascotaRepository;
import com.app.veterinaria.infrastructure.mapper.MascotaMapper;
import com.app.veterinaria.infrastructure.persistence.adapter.r2dbc.MascotaR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class MascotaRepositoryImpl implements MascotaRepository {

    private final MascotaR2dbcRepository r2dbcRepository;
    private final MascotaMapper mapper;

    @Override
    public Mono<Mascota> save(Mascota mascota) {
        return Mono.fromSupplier(() -> mapper.toEntity(mascota))
                .flatMap(r2dbcRepository::save)
                .map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsById(UUID mascotaId) {
        return r2dbcRepository.existsById(mascotaId);
    }

    @Override
    public Mono<Void> deleteById(UUID mascotaId) {
        return r2dbcRepository.deleteById(mascotaId);
    }

    @Override
    public Mono<Mascota> findById(UUID mascotaId) {
        return r2dbcRepository.findById(mascotaId).map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByUsuarioId(UUID usuarioId) {
        return r2dbcRepository.existsByUsuarioId(usuarioId);
    }

    @Override
    public Flux<Mascota> findByUsuarioId(UUID usuarioId) {
        return r2dbcRepository.findByUsuarioId(usuarioId).map(mapper::toDomain);
    }

    @Override
    public Flux<Mascota> findAll() {
        return r2dbcRepository.findAll().map(mapper::toDomain);
    }

    @Override
    public Mono<Long> count() {
        return r2dbcRepository.count();
    }

    @Override
    public Mono<Long> countByEspecie(String especie) {
        return r2dbcRepository.countByEspecie(especie);
    }

    @Override
    public Mono<Long> countByAnio(int anio) {
        return r2dbcRepository.countByAnio(anio);
    }
}
