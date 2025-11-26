package com.app.veterinaria.infrastructure.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("usuario")
public class UsuarioEntity {
    @Id
    private UUID id;
    private String nombre;
    private String correo;

    @Column("password_hash")
    private String passwordHash;

    private String telefono;
    private String dni;
    private String direccion;
    private String latitud;
    private String longitud;

    @Builder.Default
    private Boolean activo = true;

    @Builder.Default
    @Column("cuenta_no_expirada")
    private Boolean cuentaNoExpirada = true;

    @Builder.Default
    @Column("cuenta_no_bloqueada")
    private Boolean cuentaNoBloqueada = true;

    @Builder.Default
    @Column("credenciales_no_expiradas")
    private Boolean credencialesNoExpiradas = true;

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private LocalDateTime updatedAt;
}