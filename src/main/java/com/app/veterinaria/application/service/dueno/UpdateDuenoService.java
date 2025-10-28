package com.app.veterinaria.application.service.dueno;

import com.app.veterinaria.application.mapper.DuenoDtoMapper;
import com.app.veterinaria.domain.repository.DuenoRepository;
import com.app.veterinaria.infrastructure.web.dto.request.DuenoUpdateRequest;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import com.app.veterinaria.shared.exception.dueno.DuenoNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateDuenoService {

    private final DuenoDtoMapper duenoDtoMapper;
    private final DuenoRepository duenoRepository;

    public Mono<OperationResponseStatus> execute(DuenoUpdateRequest request, UUID duenoId) {
        return validateExistsDueno(duenoId)
                .then(duenoRepository.findById(duenoId))
                .doOnNext(exists -> duenoDtoMapper.updateDuenoFromRequest(request,exists))
                .flatMap(duenoRepository::update)
                .then(Mono.fromCallable(() -> {
                    log.info("Dueno con id [{}] actualizado", duenoId);
                    return  OperationResponseStatus.ok("Dueno actualizado correctamente");
                }));
    }

    private Mono<Void> validateExistsDueno(UUID duenoId) {
        return duenoRepository.existsById(duenoId)
                .filter(Boolean::booleanValue)
                .switchIfEmpty(Mono.error(new DuenoNotFoundException("Dueño no encontrado")))
                .then();
    }

}
