package com.app.veterinaria.domain.repository;

import com.app.veterinaria.domain.model.Imagen;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ImagenRepository {

    Mono<Imagen> save(Imagen imagen);
    Flux<Imagen> findAll();

}
