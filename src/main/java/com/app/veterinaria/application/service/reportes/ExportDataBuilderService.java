package com.app.veterinaria.application.service.reportes;

import com.app.veterinaria.domain.model.Dueno;
import com.app.veterinaria.domain.model.Mascota;
import com.app.veterinaria.domain.repository.MascotaRepository;
import com.app.veterinaria.domain.repository.VacunaRepository;
import com.app.veterinaria.infrastructure.web.dto.details.export.DuenoExportDTO;
import com.app.veterinaria.infrastructure.web.dto.details.export.MascotaExportDTO;
import com.app.veterinaria.infrastructure.web.dto.details.export.VacunaExportDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ExportDataBuilderService {

    private final MascotaRepository mascotaRepository;
    private final VacunaRepository vacunaRepository;

    /**
     * Construye la estructura jerárquica de datos para exportación
     * Dueño -> Mascotas -> Vacunas
     */
    public Flux<DuenoExportDTO> buildExportData(Flux<Dueno> duenosFlux) {
        return duenosFlux.flatMap(this::buildDuenoWithMascotas);
    }

    private Mono<DuenoExportDTO> buildDuenoWithMascotas(Dueno dueno) {
        return mascotaRepository.findByDuenoId(dueno.getId())
                .flatMap(this::buildMascotaWithVacunas)
                .collectList()
                .map(mascotas -> createDuenoExportDTO(dueno, mascotas));
    }

    private Mono<MascotaExportDTO> buildMascotaWithVacunas(Mascota mascota) {
        return vacunaRepository.findByMascotaId(mascota.getId())
                .map(vacuna -> new VacunaExportDTO(
                        vacuna.getTipo(),
                        vacuna.getFechaAplicacion()
                ))
                .collectList()
                .map(vacunas -> createMascotaExportDTO(mascota, vacunas));
    }

    private DuenoExportDTO createDuenoExportDTO(Dueno dueno, List<MascotaExportDTO> mascotas) {
        return new DuenoExportDTO(
                dueno.getId(),
                dueno.getNombre(),
                dueno.getDNI(),
                dueno.getDireccion(),
                dueno.getTelefono(),
                mascotas
        );
    }

    private MascotaExportDTO createMascotaExportDTO(Mascota mascota,List<VacunaExportDTO> vacunas) {
        return new MascotaExportDTO(
                mascota.getId(),
                mascota.getNombre(),
                mascota.getEspecie(),
                mascota.getRaza(),
                mascota.getSexo(),
                mascota.getColor(),
                mascota.getAnios()+", años "+mascota.getMeses()+" meses",
                vacunas
        );
    }
}