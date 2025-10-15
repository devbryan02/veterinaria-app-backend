package com.app.veterinaria.infrastructure.persistence.repository;

import com.app.veterinaria.domain.model.Dueno;
import com.app.veterinaria.domain.repository.DuenoRepository;
import com.app.veterinaria.infrastructure.mapper.DuenoMapper;
import com.app.veterinaria.infrastructure.persistence.entity.DuenoEntity;
import com.app.veterinaria.infrastructure.persistence.jpa.DuenoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DuenoRepositoyImpl implements DuenoRepository {

    private final DuenoJpaRepository  duenoJpaRepository;
    private final DuenoMapper  duenoMapper;

    @Override
    public Mono<Dueno> save(Dueno dueno) {
        DuenoEntity entity = duenoMapper.toEntity(dueno);
        return duenoJpaRepository.save(entity).map(duenoMapper::toDomain);
    }

    @Override
    public Flux<Dueno> findAll() {
        return duenoJpaRepository.findAll().map(duenoMapper::toDomain);
    }

    @Override
    public Mono<Boolean> existsByCorreo(String correo) {
        return duenoJpaRepository.existsByCorreo(correo);
    }

    @Override
    public Mono<Boolean> existsByTelefono(String telefono) {
        return duenoJpaRepository.existsByTelefono(telefono);
    }

    @Override
    public Mono<Boolean> existsByDNI(String DNI) {
        return duenoJpaRepository.existsByDNI(DNI);
    }

    @Override
    public Mono<Boolean> existsById(UUID id) {
        return duenoJpaRepository.existsById(id);
    }

    @Override
    public Mono<Dueno> findById(UUID id) {
        return duenoJpaRepository.findById(id).map(duenoMapper::toDomain);
    }
}
