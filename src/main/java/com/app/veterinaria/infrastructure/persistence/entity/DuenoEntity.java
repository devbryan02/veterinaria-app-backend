package com.app.veterinaria.infrastructure.persistence.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Table("dueno")
public class DuenoEntity {

    @Id
    private UUID id;
    private String nombre;
    private String DNI;
    private String direccion;
    private String telefono;
    private String correo;
    @Column("password_hash")
    private String passwordHash;
    private Long latitud;
    private Long longitud;

}
