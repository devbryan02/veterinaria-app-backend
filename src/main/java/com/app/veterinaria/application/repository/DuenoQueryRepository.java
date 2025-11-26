package com.app.veterinaria.application.repository;

import com.app.veterinaria.infrastructure.web.dto.details.DuenoWithCantMascotaDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface DuenoQueryRepository {
    
    Flux<DuenoWithCantMascotaDetails> findAllDueno();
    Mono<DuenoWithCantMascotaDetails> findDuenoById(UUID duenoId);
    Flux<DuenoWithCantMascotaDetails> searchDueno(String term);

}
