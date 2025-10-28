package com.app.veterinaria.application.service.dueno;

import com.app.veterinaria.domain.repository.DuenoRepository;
import com.app.veterinaria.domain.repository.MascotaRepository;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import com.app.veterinaria.shared.exception.dueno.DuenoNotFoundException;
import com.app.veterinaria.shared.exception.dueno.DuenoWithRelationsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteDuenoService {

    private final DuenoRepository duenoRepository;
    private final MascotaRepository mascotaRepository;

    public Mono<OperationResponseStatus> execute(UUID duenoId) {
        return validateExistsDueno(duenoId)
                .then(validateRelations(duenoId))
                .then(duenoRepository.deleteById(duenoId))
                .then(Mono.fromCallable(() -> {
                    log.info("Dueño con id [{}] eliminado correctamente", duenoId);
                    return OperationResponseStatus.ok("Dueño eliminado correctamente");
                }))
                .doOnError(error -> log.error("Error al eliminar dueño con id [{}]: {}", duenoId, error.getMessage()));
    }

    private Mono<Void> validateExistsDueno(UUID duenoId) {
        return duenoRepository.existsById(duenoId)
                .filter(Boolean::booleanValue)
                .switchIfEmpty(Mono.error(new DuenoNotFoundException("Dueño con id [" + duenoId + "] no encontrado")))
                .then();
    }

    private Mono<Void> validateRelations(UUID duenoId) {
        return mascotaRepository.existsByDuenoId(duenoId)
                .flatMap(hasMascotas -> {
                    if (hasMascotas) {
                        return Mono.error(new DuenoWithRelationsException(
                                "No es posible eliminar el dueño porque tiene mascotas asociadas"
                        ));
                    }
                    return Mono.empty();
                });
    }
}