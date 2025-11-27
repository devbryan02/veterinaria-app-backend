package com.app.veterinaria.domain.repository;

import com.app.veterinaria.domain.model.Usuario;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface UsuarioRepository {

    Mono<Usuario> save(Usuario usuario);
    Flux<Usuario> findAll();
    Mono<Boolean> existsByCorreo(String correo);
    Mono<Boolean> existsByTelefono(String telefono);
    Mono<Boolean> existsByDNI(String DNI);
    Mono<Boolean> existsById(UUID id);
    Mono<Usuario> findById(UUID id);
    Mono<Void> deleteById(UUID id);
    Mono<Long> count();
    Mono<Usuario> findByCorreo(String correo);

}
