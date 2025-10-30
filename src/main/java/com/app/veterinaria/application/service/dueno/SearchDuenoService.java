package com.app.veterinaria.application.service.dueno;

import com.app.veterinaria.application.mapper.DuenoDtoMapper;
import com.app.veterinaria.domain.repository.DuenoRepository;
import com.app.veterinaria.domain.repository.MascotaRepository;
import com.app.veterinaria.infrastructure.web.dto.details.DuenoDetails;
import com.app.veterinaria.shared.exception.mascota.MascotaNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchDuenoService {

    private final DuenoRepository duenoRepository;
    private final MascotaRepository mascotaRepository;
    private final DuenoDtoMapper duenoDtoMapper;

    public Flux<DuenoDetails> execute(String searchTerm) {
        return duenoRepository.search(searchTerm)
                .flatMap(dueno ->
                        mascotaRepository.findByDuenoId(dueno.getId())
                                .collectList()
                                .switchIfEmpty(Mono.error(new MascotaNotFoundException("No se encontraron mascotas registradas")))
                                .doOnNext(mascotas -> log.info("Se encontraron {} mascota(s) para el dueño {}", mascotas.size(), dueno.getNombre()))
                                .map(mascotas -> {
                                    dueno.setMascotas(mascotas);
                                    return duenoDtoMapper.toDetails(dueno);
                                })
                        );
    }
}
