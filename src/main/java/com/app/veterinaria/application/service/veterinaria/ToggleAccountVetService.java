package com.app.veterinaria.application.service.veterinaria;

import com.app.veterinaria.domain.emuns.AccionEnum;
import com.app.veterinaria.domain.repository.UsuarioRepository;
import com.app.veterinaria.infrastructure.audit.Auditable;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import com.app.veterinaria.shared.exception.user.UsuarioNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ToggleAccountVetService {

    private final UsuarioRepository usuarioRepository;

    @Auditable(action = AccionEnum.UPDATE, entity = "VETERINARIA")
    public Mono<OperationResponseStatus> toggle(UUID usuarioId, ServerWebExchange exchange) {
        return usuarioRepository.findById(usuarioId)
                .switchIfEmpty(Mono.error(new UsuarioNotFoundException("Usuario no encontrado")))
                .flatMap(usuario -> {

                    boolean nuevoEstado = !usuario.cuentaNoBloqueada();
                    var usuarioActualizado = usuario.conCuentaBloqueada(nuevoEstado);

                    String accion = nuevoEstado ? "desbloqueada" : "bloqueada";

                    return usuarioRepository.save(usuarioActualizado)
                            .doOnSuccess(u -> log.info("Cuenta de usuario {} {} exitosamente", usuarioId, accion))
                            .thenReturn(OperationResponseStatus.ok("Cuenta " + accion + " correctamente"));
                });
    }
}