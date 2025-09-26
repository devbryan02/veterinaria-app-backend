package com.app.veterinaria.domain.model;

import java.util.UUID;

public class Dueno {

    private UUID id;
    private String nombre;
    private String DNI;
    private String direccion;
    private String telefono;
    private String correo;
    private String passwordHash;
    private Long latitud;
    private Long longitud;

    //Constructor vacio
    public Dueno(){}

    //Constructor lleno
    public Dueno(UUID id, String nombre, String DNI, String direccion, String telefono, String correo, String password_hash, Long latitud, Long longitud) {
        this.id = id;
        this.nombre = nombre;
        this.DNI = DNI;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.passwordHash = password_hash;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    //Metodos Get y Set
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Long getLatitud() {
        return latitud;
    }

    public void setLatitud(Long latitud) {
        this.latitud = latitud;
    }

    public Long getLongitud() {
        return longitud;
    }

    public void setLongitud(Long longitud) {
        this.longitud = longitud;
    }
}
