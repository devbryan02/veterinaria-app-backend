package com.app.veterinaria.application.service.mascota;

import com.app.veterinaria.application.mapper.MascotaDtoMapper;
import com.app.veterinaria.domain.repository.MascotaRepository;
import com.app.veterinaria.infrastructure.web.dto.request.MascotaNewRequest;
import com.app.veterinaria.infrastructure.web.dto.request.MascotaUpdateRequest;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import com.app.veterinaria.shared.exception.mascota.MascotaNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateMascotaService {

    private final MascotaRepository mascotaRepository;
    private final MascotaDtoMapper mascotaDtoMapper;

    public Mono<OperationResponseStatus> execute(MascotaUpdateRequest mascotaNueva, UUID mascotaId){
        return validateExistsMascota(mascotaId)
                .then(mascotaRepository.findById(mascotaId))
                .doOnNext(existente ->
                        mascotaDtoMapper.updateMascotaFromRequest(mascotaNueva, existente)
                )
                .flatMap(mascotaRepository::update)
                .then(Mono.fromCallable(() -> {
                    log.info("Mascota con id [{}] actualizado.", mascotaId);
                    return OperationResponseStatus.ok("Mascota actualizado correctamente");
                }));
    }

    private Mono<Void> validateExistsMascota(UUID mascotaId) {
        return mascotaRepository.existsById(mascotaId)
                .filter(Boolean::booleanValue)
                .switchIfEmpty(Mono.error(new MascotaNotFoundException("Mascota no encontrada")))
                .then();
    }
}
