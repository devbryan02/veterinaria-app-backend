package com.app.veterinaria.domain.model;

import com.app.veterinaria.domain.record.DataReport;

import java.time.LocalDate;
import java.util.UUID;

public class Reporte {

    private UUID id;
    private LocalDate fecha;
    private String descripcion;
    private DataReport data;
    private Admin admin;

    //constructor vacio
    public Reporte(){}

    public Reporte(UUID id, LocalDate fecha, String descripcion, DataReport data, Admin admin) {
        this.id = id;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.data = data;
        this.admin = admin;
    }

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

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    public DataReport getData() {
        return data;
    }

    public void setData(DataReport data) {
        this.data = data;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
