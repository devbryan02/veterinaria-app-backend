package com.app.veterinaria.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class Vacuna {

    private UUID id;
    private String tipo;
    private LocalDate fechaAplicacion;
    private Mascota mascota;

    //constructor vacio
    public Vacuna(){}

    //constructor lleno
    public Vacuna(UUID id, String tipo, LocalDate fechaAplicacion, Mascota mascota) {
        this.id = id;
        this.tipo = tipo;
        this.fechaAplicacion = fechaAplicacion;
        this.mascota = mascota;
    }

    //metodos get y set

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public LocalDate getFechaAplicacion() {
        return fechaAplicacion;
    }

    public void setFechaAplicacion(LocalDate fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }
}
