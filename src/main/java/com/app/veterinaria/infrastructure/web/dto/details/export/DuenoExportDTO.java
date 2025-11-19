package com.app.veterinaria.infrastructure.web.dto.details.export;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DuenoExportDTO {
    private UUID id;
    private String nombre;
    private String dni;
    private String direccion;
    private String telefono;
    private List<MascotaExportDTO> mascotas;
}