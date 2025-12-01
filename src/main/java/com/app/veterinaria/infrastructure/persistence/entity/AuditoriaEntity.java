package com.app.veterinaria.infrastructure.persistence.entity;

import com.app.veterinaria.domain.emuns.AccionEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("auditoria")
public class AuditoriaEntity {

    @Id
    private UUID id;

    @Column("usuario_id")
    private UUID usuarioId;

    private AccionEnum accion;

    private String entidad;

    @Column("ip_address")
    private String ipAddress;

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;
}
