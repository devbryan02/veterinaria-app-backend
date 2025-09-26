package com.app.veterinaria.domain.repository;

import com.app.veterinaria.domain.model.AnalisisSalud;

import java.util.List;

public interface AnalisisSaludRepository {

    AnalisisSalud save(AnalisisSalud analisisSalud);
    List<AnalisisSalud> findAll();

}
