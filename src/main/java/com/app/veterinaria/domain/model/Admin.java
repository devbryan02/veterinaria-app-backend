package com.app.veterinaria.domain.model;

import com.app.veterinaria.domain.emuns.RolAdmin;

import java.util.UUID;

public class Admin {

    private UUID id;
    private String nombre;
    private String correo;
    private String passwordHash;
    private RolAdmin rol;
}
