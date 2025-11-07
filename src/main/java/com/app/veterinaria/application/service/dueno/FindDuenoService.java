package com.app.veterinaria.application.service.dueno;

import com.app.veterinaria.application.mapper.DuenoDtoMapper;
import com.app.veterinaria.domain.repository.DuenoRepository;
import com.app.veterinaria.domain.repository.MascotaRepository;
import com.app.veterinaria.infrastructure.web.dto.details.DuenoDetails;
import com.app.veterinaria.shared.exception.dueno.DuenoNotFoundException;
import com.app.veterinaria.shared.exception.mascota.MascotaNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FindDuenoService {

    private final DuenoRepository duenoRepository;
    private final DuenoDtoMapper duenoDtoMapper;
    private final MascotaRepository mascotaRepository;

    public Mono<DuenoDetails> execute(UUID duenoId) {
        return duenoRepository.findById(duenoId)
                .switchIfEmpty(Mono.error(new DuenoNotFoundException("Dueño no encontrado")))
                .doOnNext(d -> log.info("Dueno econtrado: {}", d.getNombre()))
                .flatMap(dueno ->
                        mascotaRepository.findByDuenoId(duenoId)
                                .collectList()
                                .map(mascotas -> {
                                    dueno.setMascotas(mascotas);
                                    log.info("Se encontraron {} mascota(s) para el dueño {}", mascotas.size(), dueno.getNombre());
                                    return duenoDtoMapper.toDetails(dueno);
                                })
                );
    }
}
