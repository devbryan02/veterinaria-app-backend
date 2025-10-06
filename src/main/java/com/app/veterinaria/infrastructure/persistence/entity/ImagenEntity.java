package com.app.veterinaria.infrastructure.persistence.entity;

import com.app.veterinaria.domain.model.Mascota;
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
@Table("imagen")
public class ImagenEntity {

    @Id
    private UUID id;
    private String url;
    @Column("fecha_subida")
    private LocalDate fechaSubida;
    private String descripcion;
    @Column("mascota_id")
    private UUID mascotaId;
}
