package com.app.veterinaria.application.respository;

import com.app.veterinaria.infrastructure.web.dto.details.MascotaWithDuenoDetails;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface MascotaQueryRepository {

    Flux<MascotaWithDuenoDetails> findAllWithDueno();
    Flux<MascotaWithDuenoDetails> findByFilters(String especie, String sexo, String raza);
    Flux<MascotaWithDuenoDetails> search(String term);
    Mono<MascotaWithDuenoDetails> findById(UUID mascotaId);

}
