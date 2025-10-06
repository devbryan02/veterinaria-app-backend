package com.app.veterinaria.infrastructure.persistence.repository;

import com.app.veterinaria.domain.model.Admin;
import com.app.veterinaria.domain.repository.AdminRepository;
import com.app.veterinaria.infrastructure.mapper.AdminMapper;
import com.app.veterinaria.infrastructure.persistence.entity.AdminEntity;
import com.app.veterinaria.infrastructure.persistence.jpa.AdminJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class AdminRepositoryImpl implements AdminRepository {

    private AdminMapper adminMapper;
    private AdminJpaRepository adminJpaRepository;

    @Override
    public Admin save(Admin admin) {
        AdminEntity entity = adminMapper.toEntity(admin);
        AdminEntity adminSaved = adminJpaRepository.save(entity);
        return null;
    }

    @Override
    public List<Admin> findAll() {
        return List.of();
    }
}
