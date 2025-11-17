package com.app.veterinaria.domain.model;

import lombok.ToString;

import java.util.List;
import java.util.UUID;

@ToString
public class Dueno {

    private UUID id;
    private String nombre;
    private String DNI;
    private String direccion;
    private String telefono;
    private String correo;
    private String passwordHash;
    private String latitud;
    private String longitud;

    private List<Mascota> mascotas;

    //Constructor vacio
    public Dueno(){}

    // constructor lleno
    public Dueno(UUID id, String nombre, String DNI, String direccion, String telefono, String correo, String passwordHash, String latitud, String longitud, List<Mascota> mascotas) {
        this.id = id;
        this.nombre = nombre;
        this.DNI = DNI;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.passwordHash = passwordHash;
        this.latitud = latitud;
        this.longitud = longitud;
        this.mascotas = mascotas;
    }

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

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public List<Mascota> getMascotas() {
        return mascotas;
    }

    public void setMascotas(List<Mascota> mascotas) {
        this.mascotas = mascotas;
    }
}
