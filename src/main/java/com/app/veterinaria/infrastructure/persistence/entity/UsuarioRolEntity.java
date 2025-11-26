package com.app.veterinaria.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("usuario_rol")
public class UsuarioRolEntity {

    @Id
    private UUID id;

    @Column("usuario_id")
    private UUID usuarioId;

    @Column("rol_id")
    private UUID rolId;

    @Column("asignado_en")
    private LocalDateTime asignadoEn;
}