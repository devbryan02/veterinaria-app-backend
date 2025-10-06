package com.app.veterinaria.domain.repository;

import com.app.veterinaria.domain.model.Admin;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AdminRepository {

    Mono<Admin> save(Admin admin);
    Flux<Admin> findAll();
    Mono<Admin> findByCorreo(String correo);

}
