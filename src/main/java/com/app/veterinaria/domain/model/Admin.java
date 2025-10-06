package com.app.veterinaria.domain.model;

import com.app.veterinaria.domain.emuns.RolAdmin;

import java.util.UUID;

public class Admin {

    private UUID id;
    private String nombre;
    private String correo;
    private String passwordHash;
    private RolAdmin rol;

    //constructor vacio
    public Admin() {}

    //constructor lleno
    public Admin(UUID id, String nombre, String correo, String passwordHash, RolAdmin rol) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.passwordHash = passwordHash;
        this.rol = rol;
    }

    //metodos Get y Set
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

    public RolAdmin getRol() {
        return rol;
    }

    public void setRol(RolAdmin rol) {
        this.rol = rol;
    }
}
