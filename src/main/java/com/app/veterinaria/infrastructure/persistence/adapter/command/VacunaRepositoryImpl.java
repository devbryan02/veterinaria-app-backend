package com.app.veterinaria.infrastructure.persistence.adapter.command;

import com.app.veterinaria.domain.model.Vacuna;
import com.app.veterinaria.domain.repository.VacunaRepository;
import com.app.veterinaria.infrastructure.mapper.VacunaMapper;
import com.app.veterinaria.infrastructure.persistence.adapter.r2dbc.VacunaR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class VacunaRepositoryImpl implements VacunaRepository {

    private final VacunaMapper  mapper;
    private final VacunaR2dbcRepository r2dbcRepository;

    @Override
    public Mono<Vacuna> save(Vacuna vacuna) {
        return Mono.fromSupplier(() -> mapper.toEntity(vacuna))
                .flatMap(r2dbcRepository::save)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Vacuna> findAll() {
        return r2dbcRepository.findAll().map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByMascotaId(UUID mascotaId) {
        return r2dbcRepository.existsByMascotaId(mascotaId);
    }

    @Override
    public Flux<Vacuna> findByMascotaId(UUID mascotaId) {
        return r2dbcRepository.findByMascotaId(mascotaId).map(mapper::toDomain);
    }

    @Override
    public Mono<Long> count() {
        return r2dbcRepository.countAllVacunas();
    }

    @Override
    public Mono<Long> countByMes(int mes) {
        return r2dbcRepository.countByMes(mes);
    }

    @Override
    public Mono<Long> countByAnio(int anio) {
        return r2dbcRepository.countByAnio(anio);
    }

    @Override
    public Mono<Vacuna> findById(UUID id) {
        return r2dbcRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return r2dbcRepository.deleteById(id);
    }
}
