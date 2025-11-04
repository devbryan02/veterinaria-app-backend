package com.app.veterinaria.application.service.dueno;

import com.app.veterinaria.application.mapper.DuenoDtoMapper;
import com.app.veterinaria.domain.repository.DuenoRepository;
import com.app.veterinaria.infrastructure.web.dto.request.DuenoUpdateIgnorePasswordAndLocationRequest;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import com.app.veterinaria.shared.exception.dueno.DuenoNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateDuenoIgnorePasswordAndLocationService {

    private final DuenoDtoMapper duenoDtoMapper;
    private final DuenoRepository duenoRepository;

    public Mono<OperationResponseStatus> execute(DuenoUpdateIgnorePasswordAndLocationRequest request, UUID duenoId) {
        return duenoRepository.findById(duenoId)
                .switchIfEmpty(Mono.error(new DuenoNotFoundException("Dueño no encontrado")))
                .flatMap(existing -> {
                    duenoDtoMapper.updateDuenoIgnorePasswordAndLocation(request, existing);
                    return duenoRepository.update(existing)
                            .thenReturn(OperationResponseStatus.ok("Dueño actualizado correctamente"));
                })
                .doOnSuccess(resp -> log.info("Dueño con id [{}] actualizado", duenoId));
    }

}
