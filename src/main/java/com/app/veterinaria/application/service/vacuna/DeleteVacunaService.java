package com.app.veterinaria.application.service.vacuna;

import com.app.veterinaria.domain.emuns.AccionEnum;
import com.app.veterinaria.domain.emuns.EntityEnum;
import com.app.veterinaria.domain.repository.VacunaRepository;
import com.app.veterinaria.infrastructure.audit.Auditable;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import com.app.veterinaria.shared.exception.vacuna.VacunaNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteVacunaService {

    private final VacunaRepository vacunaRepository;

    @Auditable(action = AccionEnum.DELETE, entity = EntityEnum.VACUNA)
    public Mono<OperationResponseStatus> execute(UUID vacunaId, ServerWebExchange exchange){
        return vacunaRepository.findById(vacunaId)
                .switchIfEmpty(Mono.error(new VacunaNotFoundException("Vacuna no encontrado")))
                .then(vacunaRepository.deleteById(vacunaId))
                .then(Mono.fromCallable(() -> OperationResponseStatus.ok("Vacuna eliminada correctamente")))
                .doOnError(error -> log.error("Error al eliminar vacuna con id [{}]: {}", vacunaId, error.getMessage()));
    }
}
