package com.app.veterinaria.infrastructure.persistence.entity;


import com.app.veterinaria.domain.emuns.RolAdmin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table("admin")
public class AdminEntity {

    @Id
    private UUID id;
    private String nombre;
    private String correo;
    @Column("password_hash")
    private String passwordHash;
    private RolAdmin rol;

}
