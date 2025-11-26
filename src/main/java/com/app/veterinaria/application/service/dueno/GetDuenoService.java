package com.app.veterinaria.application.service.dueno;

import com.app.veterinaria.application.repository.DuenoQueryRepository;
import com.app.veterinaria.infrastructure.web.dto.details.DuenoWithCantMascotaDetails;
import com.app.veterinaria.shared.exception.dueno.DuenoNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class GetDuenoService {

    private final DuenoQueryRepository duenoQueryRepository;

    public Mono<DuenoWithCantMascotaDetails> findById(UUID usuarioId) {
        return duenoQueryRepository.findDuenoById(usuarioId)
                .switchIfEmpty(Mono.error(new DuenoNotFoundException("Dueno no encontrado")))
                .doOnSubscribe(sub -> log.info("Buscando dueno con ID: {}", usuarioId))
                .doOnSuccess(d -> log.info("Dueno encontrado: {}", d.nombre()));
    }

    public Flux<DuenoWithCantMascotaDetails> findAllDuenos(){
        return duenoQueryRepository.findAllDueno()
                .doOnSubscribe(sub -> log.info("Listando todos los duenos"))
                .doOnComplete(() -> log.info("Listado completado"));
    }

    public Flux<DuenoWithCantMascotaDetails> searchByTerm(String term){
        return duenoQueryRepository.searchDueno(term)
                .doOnSubscribe(sub -> log.info("Buscando resultados para: {}", term))
                .doOnComplete(() -> log.info("Busqueda completada"));
    }

}
