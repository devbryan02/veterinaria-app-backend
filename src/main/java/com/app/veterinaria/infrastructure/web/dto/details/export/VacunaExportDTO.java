package com.app.veterinaria.infrastructure.web.dto.details.export;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacunaExportDTO {
    private String tipo;
    private LocalDate fechaAplicacion;
}
