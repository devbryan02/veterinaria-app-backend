package com.app.veterinaria.domain.repository;

import com.app.veterinaria.domain.model.Imagen;

import java.util.List;

public interface ImagenRepository {

    Imagen save(Imagen imagen);
    List<Imagen> findAll();

}
