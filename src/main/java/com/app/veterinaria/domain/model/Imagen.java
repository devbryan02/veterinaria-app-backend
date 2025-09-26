package com.app.veterinaria.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class Imagen {

    private UUID id;
    private String url;
    private LocalDate fechaSubida;
    private String descripcion;
    private Mascota mascota;

    //contructor vacio
    public Imagen(){}

    //contructor lleno
    public Imagen(UUID id, String url, LocalDate fechaSubida, String descripcion, Mascota mascota) {
        this.id = id;
        this.url = url;
        this.fechaSubida = fechaSubida;
        this.descripcion = descripcion;
        this.mascota = mascota;
    }

    //metodos get y set
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDate getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(LocalDate fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }
}
