package com.app.veterinaria.infrastructure.persistence.repository;

import com.app.veterinaria.domain.model.Admin;
import com.app.veterinaria.domain.repository.AdminRepository;
import com.app.veterinaria.infrastructure.mapper.AdminMapper;
import com.app.veterinaria.infrastructure.persistence.entity.AdminEntity;
import com.app.veterinaria.infrastructure.persistence.jpa.AdminJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AdminRepositoryImpl implements AdminRepository {

    private final AdminMapper adminMapper;
    private final AdminJpaRepository adminJpaRepository;

    @Override
    public Mono<Admin> save(Admin admin) {
        // Transformamos el objeto Admin a AdminEntity
        AdminEntity entity = adminMapper.toEntity(admin);
        // Guardamos el objeto en la BD.
        return adminJpaRepository.save(entity).map(adminMapper::toDomain);
    }

    @Override
    public Flux<Admin> findAll() {
        return adminJpaRepository.findAll().map(adminMapper::toDomain);
    }

    @Override
    public Mono<Admin> findByCorreo(String correo) {
        return adminJpaRepository.findByCorreo(correo).map(adminMapper::toDomain);
    }
}
