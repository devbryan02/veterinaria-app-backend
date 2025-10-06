package com.app.veterinaria.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table("vacuna")
public class VacunaEntity {

    @Id
    private UUID id;
    private String tipo;
    private LocalDate fechaAplicacion;
    @Column("mascota_id")
    private UUID mascotaId;

}
