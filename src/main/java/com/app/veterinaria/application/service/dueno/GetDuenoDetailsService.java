package com.app.veterinaria.application.service.dueno;

import com.app.veterinaria.application.mapper.DuenoDetailsMapper;
import com.app.veterinaria.domain.model.Dueno;
import com.app.veterinaria.domain.repository.DuenoRepository;
import com.app.veterinaria.domain.repository.MascotaRepository;
import com.app.veterinaria.infrastructure.web.dto.details.DuenoFullDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetDuenoDetailsService {

    private final DuenoRepository duenoRepository;
    private final MascotaRepository mascotaRepository;
    private final DuenoDetailsMapper mapper;

    public Mono<DuenoFullDetails> execute(UUID duenoId) {

        return duenoRepository.findById(duenoId)
                .switchIfEmpty(Mono.error(new RuntimeException("Dueño no encontrado")))
                .flatMap(this::loadRelations);
    }

    private Mono<DuenoFullDetails> loadRelations(Dueno dueno) {
        return mascotaRepository.findByDuenoId(dueno.getId())
                .map(mapper::toMascotaDetails)
                .collectList()
                .map(mapper::toResumen)
                .map(resumen -> mapper.toDetails(dueno, resumen));
    }
}
