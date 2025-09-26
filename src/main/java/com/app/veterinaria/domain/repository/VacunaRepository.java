package com.app.veterinaria.domain.repository;

import com.app.veterinaria.domain.model.Vacuna;

import java.util.List;

public interface VacunaRepository {

    Vacuna save(Vacuna vacuna);
    List<Vacuna> findAll();

}
