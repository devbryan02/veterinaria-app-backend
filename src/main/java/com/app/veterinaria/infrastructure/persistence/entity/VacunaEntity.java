package com.app.veterinaria.infrastructure.persistence.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("vacuna")
public class VacunaEntity {

    @Id
    private UUID id;

    @Column("mascota_id")
    private UUID mascotaId;

    private String tipo;

    @Column("fecha_aplicacion")
    private LocalDate fechaAplicacion;

    @Builder.Default
    @Column("meses_vigencia")
    private Integer mesesVigencia = 12;

    @Column("fecha_vencimiento")
    private LocalDate fechaVencimiento;

    @Column("proxima_dosis")
    private LocalDate proximaDosis;

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;
}