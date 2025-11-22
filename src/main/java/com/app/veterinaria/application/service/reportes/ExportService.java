package com.app.veterinaria.application.service.reportes;

import com.app.veterinaria.domain.model.Dueno;
import com.app.veterinaria.domain.repository.DuenoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExportService {

    private final DuenoRepository duenoRepository;
    private final ExportDataBuilderService dataBuilder;
    private final ExcelGeneratorService excelGenerator;

    /**
     * Exporta las vacunas de un dueño específico (sin filtrar por año)
     */
    public Mono<byte[]> exportVacunasByDueno(UUID duenoId) {
        Flux<Dueno> duenoFlux = duenoRepository.findById(duenoId).flux();
        return buildAndGenerateExcel(duenoFlux);
    }

    /**
     * Exporta las vacunas de todos los dueños filtrando por año
     */
    public Mono<byte[]> exportVacunasByYear(int anio) {
        Flux<Dueno> duenosFlux = duenoRepository.findAll();
        return buildAndGenerateExcelByYear(duenosFlux, anio);
    }

    /**
     * Construye y genera el Excel sin filtro (solo por dueño)
     */
    private Mono<byte[]> buildAndGenerateExcel(Flux<Dueno> duenosFlux) {
        return dataBuilder.buildExportData(duenosFlux)
                .collectList()
                .flatMap(excelGenerator::generateProfessionalExcel);
    }

    /**
     * Construye y genera el Excel filtrando por año
     */
    private Mono<byte[]> buildAndGenerateExcelByYear(Flux<Dueno> duenosFlux, int anio) {
        return dataBuilder.buildExportDataByYear(duenosFlux, anio)
                .collectList()
                .flatMap(excelGenerator::generateProfessionalExcel);
    }
}
