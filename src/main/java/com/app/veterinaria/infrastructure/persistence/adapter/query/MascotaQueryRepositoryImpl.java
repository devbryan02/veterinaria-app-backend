package com.app.veterinaria.infrastructure.persistence.adapter.query;

import com.app.veterinaria.application.repository.MascotaQueryRepository;
import com.app.veterinaria.infrastructure.persistence.adapter.r2dbc.MascotaR2dbcRepository;
import com.app.veterinaria.infrastructure.web.dto.details.MascotaWithDuenoDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MascotaQueryRepositoryImpl implements MascotaQueryRepository {

    private final MascotaR2dbcRepository jpaRepository;

    @Override
    public Flux<MascotaWithDuenoDetails> findAllWithDueno() {
        return jpaRepository.findAllWithDueno();
    }

    @Override
    public Flux<MascotaWithDuenoDetails> findByFilters(String especie, String sexo, String raza) {
        return jpaRepository.findByFilters(especie, sexo, raza);
    }

    @Override
    public Flux<MascotaWithDuenoDetails> search(String term) {
        final int LIMIT_SIZE = 20;
        return jpaRepository.search(term).take(LIMIT_SIZE);
    }

    @Override
    public Mono<MascotaWithDuenoDetails> findById(UUID mascotaId) {
        return jpaRepository.findByIdDetails(mascotaId);
    }
}
