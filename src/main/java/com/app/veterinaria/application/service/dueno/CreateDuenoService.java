package com.app.veterinaria.application.service.dueno;

import com.app.veterinaria.application.mapper.DuenoDtoMapper;
import com.app.veterinaria.domain.repository.DuenoRepository;
import com.app.veterinaria.infrastructure.web.dto.request.DuenoNewRequest;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import com.app.veterinaria.shared.exception.dueno.DuenoCreateException;
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
    private final ValidationDuenoService validationDuenoService;

    public Mono<OperationResponseStatus> execute(DuenoNewRequest request) {
        return validationDuenoService.validateUniqueness(request.DNI(), request.telefono(),request.correo())
                .then(Mono.fromCallable(() -> duenoDtoMapper.toDomain(request)))
                .flatMap(duenoRepository::save)
                .map(duenoSaved -> {
                    log.info("Dueño registrado correctamente con id: {}", duenoSaved.getId());
                    return OperationResponseStatus.ok("Dueño registrado correctamente");
                })
                .onErrorMap(ex -> !(ex instanceof DuenoCreateException), ex-> {
                    log.error("Error inesperado al crear dueño con DNI: {}", request.DNI());
                    return new DuenoCreateException(ex.getMessage());
                })
                .doOnError(DuenoCreateException.class, ex ->
                        log.warn("Operación de creación falló para DNI: {} - {}",
                                request.DNI(), ex.getMessage())
                );
    }

}
