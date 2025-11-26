package com.app.veterinaria.infrastructure.persistence.entity;

import com.app.veterinaria.domain.emuns.EstadoMascota;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("mascota")
public class MascotaEntity {
    @Id
    private UUID id;

    @Column("usuario_id")
    private UUID usuarioId;
    private String identificador;
    private String nombre;
    private String especie;
    private String raza;
    private String sexo;
    private String temperamento;

    @Column("condicion_reproductiva")
    private String condicionReproductiva;

    private String color;

    @Builder.Default
    private Integer anios = 0;

    @Builder.Default
    private Integer meses = 0;

    EstadoMascota estado;

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column("updated_at")
    private LocalDateTime updatedAt;
}

