package com.app.veterinaria.domain.repository;

import com.app.veterinaria.domain.model.Admin;

import java.util.List;

public interface AdminRepository {

    Admin save(Admin admin);
    List<Admin> findAll();

}
