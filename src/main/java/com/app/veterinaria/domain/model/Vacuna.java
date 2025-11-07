package com.app.veterinaria.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class Vacuna {

    private UUID id;
    private String tipo;
    private LocalDate fechaAplicacion;
    private Mascota mascota;
    private Integer mesesVigencia = 12; // Default 12 meses
    private LocalDate fechaVencimiento;
    private LocalDate proximaDosis;

    //constructor vacio
    public Vacuna(){}

    public Vacuna(UUID id, String tipo, Mascota mascota, LocalDate fechaAplicacion, Integer mesesVigencia, LocalDate fechaVencimiento, LocalDate proximaDosis) {
        this.id = id;
        this.tipo = tipo;
        this.mascota = mascota;
        this.fechaAplicacion = fechaAplicacion;
        this.mesesVigencia = mesesVigencia;
        this.fechaVencimiento = fechaVencimiento;
        this.proximaDosis = proximaDosis;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDate getFechaAplicacion() {
        return fechaAplicacion;
    }

    public void setFechaAplicacion(LocalDate fechaAplicacion) {
        this.fechaAplicacion = fechaAplicacion;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Integer getMesesVigencia() {
        return mesesVigencia;
    }

    public void setMesesVigencia(Integer mesesVigencia) {
        this.mesesVigencia = mesesVigencia;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public LocalDate getProximaDosis() {
        return proximaDosis;
    }

    public void setProximaDosis(LocalDate proximaDosis) {
        this.proximaDosis = proximaDosis;
    }

    // Metodo helper para calcular vencimiento
    public void calcularFechaVencimiento() {
        if (this.fechaAplicacion != null && this.mesesVigencia != null) {
            this.fechaVencimiento = this.fechaAplicacion.plusMonths(this.mesesVigencia);
        }
    }

    // Metodo para saber si está vigente
    public boolean estaVigente() {
        if (this.fechaVencimiento == null) return false;
        return LocalDate.now().isBefore(this.fechaVencimiento) ||
                LocalDate.now().isEqual(this.fechaVencimiento);
    }
}
