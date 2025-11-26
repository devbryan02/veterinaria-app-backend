package com.app.veterinaria.application.service.dueno;

import com.app.veterinaria.application.mapper.request.DuenoRequestMapper;
import com.app.veterinaria.domain.repository.UsuarioRepository;
import com.app.veterinaria.infrastructure.web.dto.request.DuenoUpdateRequest;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import com.app.veterinaria.shared.exception.dueno.DuenoNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateDuenoService {

    private final DuenoRequestMapper mapper;
    private final UsuarioRepository usuarioRepository;

    public Mono<OperationResponseStatus> execute(DuenoUpdateRequest request, UUID usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .switchIfEmpty(Mono.error(new DuenoNotFoundException("Dueño no encontrado")))
                .flatMap(existing -> {
                    var actualizado = mapper.toUpdate(request, existing);
                    return usuarioRepository.save(actualizado)
                            .thenReturn(OperationResponseStatus.ok("Dueño actualizado correctamente"));
                })
                .doOnSuccess(resp -> log.info("Dueño con id [{}] actualizado", usuarioId));
    }
}
