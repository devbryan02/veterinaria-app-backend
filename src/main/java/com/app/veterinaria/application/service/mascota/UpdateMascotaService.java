package com.app.veterinaria.application.service.mascota;

import com.app.veterinaria.application.mapper.request.MascotaRequestMapper;
import com.app.veterinaria.domain.emuns.AccionEnum;
import com.app.veterinaria.domain.emuns.EntityEnum;
import com.app.veterinaria.domain.repository.MascotaRepository;
import com.app.veterinaria.infrastructure.audit.Auditable;
import com.app.veterinaria.infrastructure.web.dto.request.MascotaUpdateRequest;
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
public class UpdateMascotaService {

    private final MascotaRepository mascotaRepository;
    private final MascotaRequestMapper mapper;

    @Auditable(action = AccionEnum.UPDATE, entity = EntityEnum.MASCOTA)
    public Mono<OperationResponseStatus> execute(MascotaUpdateRequest request, UUID mascotaId, ServerWebExchange exchange){
        return mascotaRepository.findById(mascotaId)
                .switchIfEmpty(Mono.error(new MascotaNotFoundException("Mascota no encontrada")))
                .flatMap(existing -> {
                    var actualizado = mapper.toUpdate(request, existing);
                    return mascotaRepository.save(actualizado)
                            .thenReturn(OperationResponseStatus.ok("Mascota actualizada"));
                })
                .doOnSuccess(resp -> log.info("Dueno con id: {} actualizado", mascotaId));
    }
}
