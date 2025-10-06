package com.app.veterinaria.domain.model;

import com.app.veterinaria.domain.record.ResultAI;

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

    //contructo vacio
    public AnalisisSalud() {}

    //constructor lleno
    public AnalisisSalud(UUID id, LocalDate fecha, ResultAI resultado, Long confianza, String observaciones, Mascota mascota, Admin admin) {
        this.id = id;
        this.fecha = fecha;
        this.resultado = resultado;
        this.confianza = confianza;
        this.observaciones = observaciones;
        this.mascota = mascota;
        this.admin = admin;
    }

    //Metodos get y set
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public ResultAI getResultado() {
        return resultado;
    }

    public void setResultado(ResultAI resultado) {
        this.resultado = resultado;
    }

    public Long getConfianza() {
        return confianza;
    }

    public void setConfianza(Long confianza) {
        this.confianza = confianza;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}
