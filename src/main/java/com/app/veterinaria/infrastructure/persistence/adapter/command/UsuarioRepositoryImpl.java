package com.app.veterinaria.infrastructure.persistence.adapter.command;

import com.app.veterinaria.domain.model.Usuario;
import com.app.veterinaria.domain.repository.UsuarioRepository;
import com.app.veterinaria.infrastructure.mapper.UsuarioMapper;
import com.app.veterinaria.infrastructure.persistence.adapter.r2dbc.UsuarioR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final UsuarioR2dbcRepository r2dbcRepository;
    private final UsuarioMapper mapper;

    @Override
    public Mono<Usuario> save(Usuario usuario) {
        return Mono.fromSupplier(()-> mapper.toEntity(usuario))
                .flatMap(r2dbcRepository::save)
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Usuario> findAll() {
        return r2dbcRepository.findAll().map(mapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByCorreo(String correo) {
        return r2dbcRepository.existsByCorreo(correo);
    }

    @Override
    public Mono<Boolean> existsByTelefono(String telefono) {
        return r2dbcRepository.existsByTelefono(telefono);
    }

    @Override
    public Mono<Boolean> existsByDNI(String DNI) {
        return r2dbcRepository.existsByDni(DNI);
    }

    @Override
    public Mono<Boolean> existsById(UUID id) {
        return r2dbcRepository.existsById(id);
    }

    @Override
    public Mono<Usuario> findById(UUID id) {
        return r2dbcRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        return r2dbcRepository.deleteById(id);
    }

    @Override
    public Mono<Long> count() {
        return r2dbcRepository.count();
    }

    @Override
    public Mono<Usuario> findByCorreo(String correo) {
        return r2dbcRepository.findByCorreo(correo).map(mapper::toDomain);
    }
}
