package com.app.veterinaria.domain.repository;

import com.app.veterinaria.domain.model.Reporte;

import java.util.List;

public interface ReporteRepository {

    Reporte save(Reporte reporte);
    List<Reporte> findAll();

}
