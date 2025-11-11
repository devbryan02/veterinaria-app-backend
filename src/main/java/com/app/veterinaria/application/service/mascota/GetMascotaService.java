package com.app.veterinaria.application.service.mascota;

import com.app.veterinaria.application.repository.MascotaQueryRepository;
import com.app.veterinaria.infrastructure.web.dto.details.MascotaWithDuenoDetails;
import com.app.veterinaria.shared.exception.mascota.MascotaNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetMascotaService {

    private final MascotaQueryRepository mascotaQueryRepository;

    public Mono<MascotaWithDuenoDetails> findById(UUID id) {
        return mascotaQueryRepository.findById(id)
                .switchIfEmpty(Mono.error(new MascotaNotFoundException("Mascota con ID " + id + " no encontrada")))
                .doOnSubscribe(sub -> log.info("Buscando mascota con ID: {}", id))
                .doOnNext(m -> log.info("Mascota encontrada: {}", m.nombre()));
    }

    public Flux<MascotaWithDuenoDetails> findAll() {
        return mascotaQueryRepository.findAllWithDueno()
                .doOnSubscribe(sub -> log.info("Listando todas las mascotas"))
                .doOnComplete(() -> log.info("Listado completado"));
    }

    public Flux<MascotaWithDuenoDetails> search(String term) {
        return mascotaQueryRepository.search(term)
                .doOnSubscribe(sub -> log.info("Buscando mascotas con término: {}", term))
                .doOnComplete(() -> log.info("Búsqueda completada para término: {}", term));
    }

    public Flux<MascotaWithDuenoDetails> findByFilters(String especie, String sexo, String raza) {
        return mascotaQueryRepository.findByFilters(especie, sexo, raza)
                .doOnSubscribe(sub -> log.info("Filtrando mascotas - especie={}, sexo={}, raza={}", especie, sexo, raza))
                .doOnComplete(() -> log.info("Filtrado completado"));
    }
}
