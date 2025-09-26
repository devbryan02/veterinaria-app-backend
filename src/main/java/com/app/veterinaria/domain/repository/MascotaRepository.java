package com.app.veterinaria.domain.repository;

import com.app.veterinaria.domain.model.Mascota;

import java.util.List;

public interface MascotaRepository {

    Mascota save(Mascota mascota);
    List<Mascota> findAll();

}
