package com.app.veterinaria.domain.model;

import com.app.veterinaria.domain.emuns.ResultAI;

import java.time.LocalDate;
import java.util.UUID;

public class AnalisisSalud {

    private UUID id;
    private LocalDate fecha;
    private ResultAI resultado;
    private Long confianza;
    private String observaciones;
    private Mascota mascota;
    private Admin admin;


}
