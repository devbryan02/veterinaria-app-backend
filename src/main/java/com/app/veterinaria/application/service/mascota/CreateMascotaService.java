package com.app.veterinaria.application.service.mascota;

import com.app.veterinaria.application.mapper.MascotaDtoMapper;
import com.app.veterinaria.domain.repository.DuenoRepository;
import com.app.veterinaria.domain.repository.MascotaRepository;
import com.app.veterinaria.infrastructure.web.dto.request.MascotaNewRequest;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import com.app.veterinaria.shared.exception.dueno.DuenoNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateMascotaService {

    private final MascotaRepository mascotaRepository;
    private final MascotaDtoMapper mascotaDtoMapper;
    private final DuenoRepository duenoRepository;

    public Mono<OperationResponseStatus> execute(MascotaNewRequest request){
        return validateExistsDueno(UUID.fromString(request.duenoId()))
                .then(Mono.fromCallable(() -> mascotaDtoMapper.toDomain(request)))
                .flatMap(mascotaRepository::save)
                .doOnNext(m -> log.info("Mascota registrado con id: {}", m.getId()))
                .map(saved ->  OperationResponseStatus.ok("Mascota registrado correctamente"));
    }

    private Mono<Void> validateExistsDueno(UUID duenoId){
        return duenoRepository.existsById(duenoId)
                .filter(exists -> exists)
                .switchIfEmpty(Mono.error(
                        new DuenoNotFoundException("Dueño no encontrado con id: "+duenoId)
                ))
                .then();
    }

}
