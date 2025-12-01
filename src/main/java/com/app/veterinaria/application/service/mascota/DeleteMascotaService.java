package com.app.veterinaria.application.service.mascota;

import com.app.veterinaria.domain.emuns.AccionEnum;
import com.app.veterinaria.domain.repository.MascotaRepository;
import com.app.veterinaria.infrastructure.audit.Auditable;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import com.app.veterinaria.shared.exception.mascota.MascotaNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DeleteMascotaService {

    private final MascotaRepository mascotaRepository;
    private final ValidateMascotaRelationsService validateMascotaRelationsService;

    @Auditable(action = AccionEnum.DELETE, entity = "MASCOTA")
    public Mono<OperationResponseStatus> execute(UUID mascotaId, ServerWebExchange exchange){
        return validateExistsMascota(mascotaId)
                .then(validateMascotaRelationsService.execute(mascotaId))
                .then(mascotaRepository.deleteById(mascotaId))
                .then(Mono.fromCallable(() -> {
                    log.info("Mascota con id [{}] eliminado", mascotaId);
                    return OperationResponseStatus.ok("Mascota eliminado correctamente");
                }));
    }

    private Mono<Void>validateExistsMascota(UUID mascotaId){
        return mascotaRepository.existsById(mascotaId)
                .filter(Boolean::booleanValue)
                .switchIfEmpty(Mono.error(
                        new MascotaNotFoundException("Mascota con encontrada con id: "+mascotaId)
                ))
                .then();
    }

}
