package com.app.veterinaria.infrastructure.persistence.repository;

import com.app.veterinaria.application.repository.DuenoQueryRepository;
import com.app.veterinaria.infrastructure.persistence.jpa.DuenoJpaRepository;
import com.app.veterinaria.infrastructure.web.dto.details.DuenoWithCantMascotaDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DuenoQueryRepositoryImpl implements DuenoQueryRepository {

    private final DuenoJpaRepository jpaRepository;

    @Override
    public Flux<DuenoWithCantMascotaDetails> findAllDueno() {
        return jpaRepository.findAllWithLimit();
    }

    @Override
    public Mono<DuenoWithCantMascotaDetails> findDuenoById(UUID duenoId) {
        return jpaRepository.findByIdDueno(duenoId);
    }

    @Override
    public Flux<DuenoWithCantMascotaDetails> search(String term) {
        return jpaRepository.searchByTerm(term);
    }
}
