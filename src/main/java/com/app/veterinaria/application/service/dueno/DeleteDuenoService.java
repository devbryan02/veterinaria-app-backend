package com.app.veterinaria.application.service.dueno;

import com.app.veterinaria.domain.emuns.AccionEnum;
import com.app.veterinaria.domain.repository.MascotaRepository;
import com.app.veterinaria.domain.repository.UsuarioRepository;
import com.app.veterinaria.infrastructure.audit.Auditable;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import com.app.veterinaria.shared.exception.dueno.DuenoNotFoundException;
import com.app.veterinaria.shared.exception.dueno.DuenoWithRelationsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteDuenoService {

    private final UsuarioRepository usuarioRepository;
    private final MascotaRepository mascotaRepository;

    @Auditable(action = AccionEnum.DELETE, entity = "DUENO")
    public Mono<OperationResponseStatus> execute(UUID usuarioId, ServerWebExchange exchange) {
        return validateExistsDueno(usuarioId)
                .then(validateRelations(usuarioId))
                .then(usuarioRepository.deleteById(usuarioId))
                .then(Mono.fromCallable(() -> {
                    log.info("Dueño con id [{}] eliminado correctamente", usuarioId);
                    return OperationResponseStatus.ok("Dueño eliminado correctamente");
                }))
                .doOnError(error -> log.error("Error al eliminar dueño con id [{}]: {}", usuarioId, error.getMessage()));
    }

    private Mono<Void> validateExistsDueno(UUID usuarioId) {
        return usuarioRepository.existsById(usuarioId)
                .filter(Boolean::booleanValue)
                .switchIfEmpty(Mono.error(new DuenoNotFoundException("Dueño con id [" + usuarioId + "] no encontrado")))
                .then();
    }

    private Mono<Void> validateRelations(UUID usuarioId) {
        return mascotaRepository.existsByUsuarioId(usuarioId)
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