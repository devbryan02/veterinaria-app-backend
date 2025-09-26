package com.app.veterinaria.domain.repository;

import com.app.veterinaria.domain.model.Dueno;

import java.util.List;

public interface DuenoRepository {

    Dueno save(Dueno dueno);
    List<Dueno> findAll();

}
