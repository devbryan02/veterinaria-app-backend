package com.app.veterinaria.application.repository;

import com.app.veterinaria.domain.model.Usuario;
import reactor.core.publisher.Mono;

public interface AuthQueryRepository {

    Mono<Usuario> findByCorreoWithRoles(String correo);
}