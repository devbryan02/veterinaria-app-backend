package com.app.veterinaria.application.service.dueno;

import com.app.veterinaria.application.mapper.DuenoDtoMapper;
import com.app.veterinaria.domain.repository.DuenoRepository;
import com.app.veterinaria.infrastructure.web.dto.request.DuenoNewRequest;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateDuenoService {

    private final DuenoDtoMapper  duenoDtoMapper;
    private final DuenoRepository  duenoRepository;
    private final ValidationUniquesDuenoService validationUniquesDuenoService;

    public Mono<OperationResponseStatus> execute(DuenoNewRequest request) {
        return validationUniquesDuenoService.validateUniqueness(request.DNI(), request.telefono(),request.correo())
                .then(Mono.fromCallable(() -> duenoDtoMapper.toDomain(request)))
                .flatMap(duenoRepository::save)
                .doOnNext(d -> log.info("Dueño registrado con id: {}", d.getId()))
                .map(saved -> OperationResponseStatus.ok("Dueno registrado correctamente"));
    }

}
