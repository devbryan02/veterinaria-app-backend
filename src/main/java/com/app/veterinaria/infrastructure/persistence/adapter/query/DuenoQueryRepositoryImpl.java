package com.app.veterinaria.infrastructure.persistence.adapter.query;

import com.app.veterinaria.application.repository.DuenoQueryRepository;
import com.app.veterinaria.infrastructure.persistence.adapter.r2dbc.UsuarioR2dbcRepository;
import com.app.veterinaria.infrastructure.web.dto.details.DuenoWithCantMascotaDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DuenoQueryRepositoryImpl implements DuenoQueryRepository {

    private final UsuarioR2dbcRepository r2dbcRepository;

    @Override
    public Flux<DuenoWithCantMascotaDetails> findAllDueno() {
        return r2dbcRepository.findAllDuenosWithLimit();
    }

    @Override
    public Mono<DuenoWithCantMascotaDetails> findDuenoById(UUID duenoId) {
        return r2dbcRepository.findByUsuarioDuenoId(duenoId);
    }

    @Override
    public Flux<DuenoWithCantMascotaDetails> searchDueno(String term) {
        return r2dbcRepository.searchByTerm(term);
    }
}
