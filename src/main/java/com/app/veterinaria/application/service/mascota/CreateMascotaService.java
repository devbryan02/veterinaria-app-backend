package com.app.veterinaria.application.service.mascota;

import com.app.veterinaria.application.mapper.request.MascotaRequestMapper;
import com.app.veterinaria.domain.repository.MascotaRepository;
import com.app.veterinaria.domain.repository.UsuarioRepository;
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
    private final MascotaRequestMapper mapper;
    private final UsuarioRepository userRepository;

    public Mono<OperationResponseStatus> execute(MascotaNewRequest request){
        return validateExistsDueno(UUID.fromString(request.usuarioId()))
                .then(Mono.fromCallable(() -> mapper.toDomain(request)))
                .flatMap(mascotaRepository::save)
                .doOnNext(m -> log.info("Mascota registrado con id: {}", m.id()))
                .map(saved ->  OperationResponseStatus.ok("Mascota registrado correctamente"));
    }

    private Mono<Void> validateExistsDueno(UUID duenoId){
        return userRepository.existsById(duenoId)
                .filter(exists -> exists)
                .switchIfEmpty(Mono.error(
                        new DuenoNotFoundException("Dueño no encontrado con id: "+duenoId)
                ))
                .then();
    }
}
