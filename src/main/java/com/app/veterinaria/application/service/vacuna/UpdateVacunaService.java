package com.app.veterinaria.application.service.vacuna;

import com.app.veterinaria.application.mapper.request.VacunaRequestMapper;
import com.app.veterinaria.domain.emuns.AccionEnum;
import com.app.veterinaria.domain.emuns.EntityEnum;
import com.app.veterinaria.domain.repository.VacunaRepository;
import com.app.veterinaria.infrastructure.audit.Auditable;
import com.app.veterinaria.infrastructure.web.dto.request.VacunaUpdateRequest;
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
public class UpdateVacunaService {

    private final VacunaRepository vacunaRepository;
    private final VacunaRequestMapper mapper;

    @Auditable(action = AccionEnum.UPDATE, entity = EntityEnum.VACUNA)
    public Mono<OperationResponseStatus> execute(VacunaUpdateRequest request, UUID vacunaId, ServerWebExchange exchange){
        return vacunaRepository.findById(vacunaId)
                .switchIfEmpty(Mono.error(new VacunaNotFoundException("Vacuna no encontrado")))
                .flatMap(exiting -> {
                    var actualizado = mapper.toUpdate(request,exiting);
                    return vacunaRepository.save(actualizado)
                            .thenReturn(OperationResponseStatus.ok("Vacuna actualizada"));
                })
                .doOnSuccess(resp -> log.info("Vacuna con id: {} actualizada", vacunaId));
    }
}
