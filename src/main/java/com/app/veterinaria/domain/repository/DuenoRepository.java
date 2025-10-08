package com.app.veterinaria.domain.repository;

import com.app.veterinaria.domain.model.Dueno;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface DuenoRepository {

    Mono<Dueno> save(Dueno dueno);
    Flux<Dueno> findAll();
    Mono<Boolean> existsByCorreo(String correo);
    Mono<Boolean> existsByTelefono(String telefono);
    Mono<Boolean> existsByDNI(String DNI);

}
