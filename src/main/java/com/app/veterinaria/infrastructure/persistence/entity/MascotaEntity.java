package com.app.veterinaria.infrastructure.persistence.entity;

import com.app.veterinaria.domain.emuns.EstadoMascota;
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
@Table("mascota")
public class MascotaEntity {

    @Id
    private UUID id;
    private String nombre;
    private String especie;
    private String raza;
    private String sexo;
    private String temperamento;
    @Column("condicion_reproductiva")
    private String condicionReproductiva;
    private String color;
    @Column("dueno_id")
    private UUID duenoId;
    @Column("fecha_creacion")
    private LocalDate fechaCreacion;
    private String identificador;
    private Integer anios;
    private Integer meses;
    private EstadoMascota estado;

}
