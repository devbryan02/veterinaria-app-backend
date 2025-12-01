package com.app.veterinaria.application.service.reportes;

import com.app.veterinaria.domain.model.Usuario;
import com.app.veterinaria.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExportService {

    private final UsuarioRepository usuarioRepository;
    private final ExportDataBuilderService dataBuilder;
    private final ExcelGeneratorService excelGenerator;

    /**
     * Exporta las vacunas de un dueño específico (sin filtrar por año)
     */
    public Mono<byte[]> exportVacunasByDueno(UUID duenoId) {
        Flux<Usuario> usuarioFlux = usuarioRepository.findById(duenoId).flux();
        return buildAndGenerateExcel(usuarioFlux);
    }

    /**
     * Exporta las vacunas de todos los dueños filtrando por año
     */
    public Mono<byte[]> exportVacunasByYear(int anio) {
        Flux<Usuario> usuarioFlux = usuarioRepository.findAllDuenos();
        return buildAndGenerateExcelByYear(usuarioFlux, anio);
    }

    /**
     * Construye y genera el Excel sin filtro (solo por dueño)
     */
    private Mono<byte[]> buildAndGenerateExcel(Flux<Usuario> usuarioFlux) {
        return dataBuilder.buildExportData(usuarioFlux)
                .collectList()
                .flatMap(excelGenerator::generateProfessionalExcel);
    }

    /**
     * Construye y genera el Excel filtrando por año
     */
    private Mono<byte[]> buildAndGenerateExcelByYear(Flux<Usuario> usuarioFlux, int anio) {
        return dataBuilder.buildExportDataByYear(usuarioFlux, anio)
                .collectList()
                .flatMap(excelGenerator::generateProfessionalExcel);
    }
}
