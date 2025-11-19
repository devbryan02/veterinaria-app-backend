package com.app.veterinaria.infrastructure.web.dto.details.export;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MascotaExportDTO {
    private UUID id;
    private String nombre;
    private String especie;
    private String raza;
    private String sexo;
    private String color;
    private String edad;
    private List<VacunaExportDTO> vacunas;
}