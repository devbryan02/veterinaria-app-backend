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
@Table("imagen")
public class ImagenEntity {
    @Id
    private UUID id;

    @Column("mascota_id")
    private UUID mascotaId;

    private String url;
    private String descripcion;

    @Builder.Default
    @Column("fecha_subida")
    private LocalDate fechaSubida = LocalDate.now();

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("public_id")
    String publicId;
}