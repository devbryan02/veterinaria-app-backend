package com.app.veterinaria.application.service.dueno;

import com.app.veterinaria.application.mapper.request.DuenoRequestMapper;
import com.app.veterinaria.domain.emuns.AccionEnum;
import com.app.veterinaria.domain.model.Usuario;
import com.app.veterinaria.domain.model.UsuarioRol;
import com.app.veterinaria.domain.repository.RolRepository;
import com.app.veterinaria.domain.repository.UsuarioRepository;
import com.app.veterinaria.domain.repository.UsuarioRolRepository;
import com.app.veterinaria.infrastructure.audit.Auditable;
import com.app.veterinaria.infrastructure.web.dto.request.DuenoNewRequest;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import com.app.veterinaria.shared.exception.rol.RolNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateDuenoService {

    private final UsuarioRepository usuarioRepository;
    private final DuenoRequestMapper mapper;
    private final RolRepository rolRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final ValidationUniquesUsuarioService validationUniquesUsuarioService;

    private static final String DUENO_ROL = "DUENO";

    @Transactional
    public Mono<OperationResponseStatus> execute(DuenoNewRequest request) {
        return validationUniquesUsuarioService.validateUniqueness(request.dni(), request.telefono(), request.correo())
                .then(crearUsuarioConRol(request))
                .doOnSuccess(usuario -> log.info("Usuario registrado con id: {}", usuario.id()))
                .map(usuario -> OperationResponseStatus.ok("Usuario registrado correctamente"));
    }

    public Mono<Usuario> crearUsuarioConRol(DuenoNewRequest request) {
        return Mono.fromCallable(() -> mapper.toDomain(request))
                .flatMap(usuarioRepository::save)
                .flatMap(this::asignarRol);
    }

    private Mono<Usuario> asignarRol(Usuario usuario) {
        return rolRepository.findByNombre(DUENO_ROL)
                .switchIfEmpty(Mono.error(new RolNotFoundException("Rol no encontrado")))
                .flatMap(rol -> {
                    var usuarioRol = new UsuarioRol(
                            null,
                            usuario.id(),
                            rol.id(),
                            LocalDateTime.now()
                    );
                    return usuarioRolRepository.save(usuarioRol);
                })
                .thenReturn(usuario);
    }
}
