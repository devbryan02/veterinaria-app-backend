package com.app.veterinaria.application.service.mascota;

import com.app.veterinaria.application.mapper.MascotaDtoMapper;
import com.app.veterinaria.domain.repository.DuenoRepository;
import com.app.veterinaria.domain.repository.MascotaRepository;
import com.app.veterinaria.infrastructure.web.dto.details.MascotaWithDuenoDetails;
import com.app.veterinaria.shared.exception.dueno.DuenoNotFoundException;
import com.app.veterinaria.shared.exception.mascota.MascotaNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FindMascotaService {

    private final MascotaRepository mascotaRepository;
    private final DuenoRepository duenoRepository;
    private final MascotaDtoMapper mascotaDtoMapper;

    public Mono<MascotaWithDuenoDetails> execute(UUID mascotaId) {
        return mascotaRepository.findById(mascotaId)
                .switchIfEmpty(Mono.error(new MascotaNotFoundException("Mascota no encontrada")))
                .doOnNext(m -> log.info("Mascota encontrada: {}", m.getNombre()))
                .flatMap(mascota ->
                        duenoRepository.findById(mascota.getDueno().getId())
                                .switchIfEmpty(Mono.error(new DuenoNotFoundException("Dueño no encontrado")))
                                .doOnNext(d -> log.info("Dueño encontrado: {}", d.getNombre()))
                                .map(dueno -> {
                                    mascota.setDueno(dueno);
                                    return mascotaDtoMapper.toDetails(mascota);
                                }));
    }
}
