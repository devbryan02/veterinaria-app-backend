package com.app.veterinaria.application.service.vacuna;

import com.app.veterinaria.application.mapper.VacunaDtoMapper;
import com.app.veterinaria.domain.repository.MascotaRepository;
import com.app.veterinaria.domain.repository.VacunaRepository;
import com.app.veterinaria.infrastructure.web.dto.request.VacunaNewRequest;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import com.app.veterinaria.shared.exception.mascota.MascotaNotFoundException;
import com.app.veterinaria.shared.exception.vacuna.VacunaCreateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateVacunaService {

    private final VacunaDtoMapper vacunaDtoMapper;
    private final VacunaRepository vacunaRepository;
    private final MascotaRepository mascotaRepository;

    public Mono<OperationResponseStatus> execute(VacunaNewRequest request){
        return validateExistsMascota(UUID.fromString(request.mascotaId()))
                .then(Mono.fromCallable(() -> vacunaDtoMapper.toDomain(request)))
                .flatMap(vacunaRepository::save)
                .doOnNext(v -> log.info("Vacuna registrado correctamente con id:{}", v.getId()))
                .map(saved -> OperationResponseStatus.ok("Vacuna registrado correctamente"));
    }

    private Mono<Void> validateExistsMascota(UUID mascotaId){
        return mascotaRepository.existsById(mascotaId)
                .filter(exists -> exists)
                .switchIfEmpty(Mono.error(
                        new MascotaNotFoundException("Mascota no encontrada con id: "+mascotaId)
                ))
                .then();
    }

}
