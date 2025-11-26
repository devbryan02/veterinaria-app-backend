package com.app.veterinaria.infrastructure.persistence.adapter.query;

import com.app.veterinaria.application.repository.VacunaQueryRepository;
import com.app.veterinaria.infrastructure.persistence.adapter.r2dbc.VacunaR2dbcRepository;
import com.app.veterinaria.infrastructure.web.dto.details.VacunaWithMascotaDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class VacunaQueryRepositoryImpl implements VacunaQueryRepository {

    private final VacunaR2dbcRepository jpaRepository;

    @Override
    public Mono<VacunaWithMascotaDetails> findById(UUID idVacuna) {
        return jpaRepository.findByIdVacuna(idVacuna);
    }

    @Override
    public Flux<VacunaWithMascotaDetails> findAllVacunaWithMascota() {
        return jpaRepository.findAllVacunas();
    }

    @Override
    public Flux<VacunaWithMascotaDetails> findVacunasByDate(LocalDate startDate, LocalDate endDate) {
        return jpaRepository.findBydate(startDate, endDate);
    }

    @Override
    public Flux<VacunaWithMascotaDetails> filteByTipo(String tipo) {
        return jpaRepository.filterByTipo(tipo);
    }
}
