package com.app.veterinaria.infrastructure.persistence.entity;

import com.app.veterinaria.domain.record.DataReport;
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
@Table("reporte")
public class ReporteEntity {

    @Id
    private UUID id;
    private LocalDate fecha;
    private String descripcion;
    private String data;
    @Column("admin_id")
    private UUID adminId;
}
