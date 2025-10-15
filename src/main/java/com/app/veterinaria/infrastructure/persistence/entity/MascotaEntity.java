package com.app.veterinaria.infrastructure.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

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
    private String edad;
    private String sexo;
    private String temperamento;
    @Column("condicion_reproductiva")
    private String condicionReproductiva;
    private String color;
    @Column("dueno_id")
    private UUID duenoId;

}
