package com.app.veterinaria.domain.model;

import com.app.veterinaria.domain.emuns.DataReport;

import java.time.LocalDate;
import java.util.UUID;

public class Reporte {

    private UUID id;
    private LocalDate fecha;
    private String descripcion;
    private DataReport data;
    private Admin admin;
}
