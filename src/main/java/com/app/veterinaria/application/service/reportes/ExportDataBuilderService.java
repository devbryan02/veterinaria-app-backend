package com.app.veterinaria.application.service.reportes;

import com.app.veterinaria.domain.model.Mascota;
import com.app.veterinaria.domain.model.Usuario;
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


    // -----------------------------------------------------
    //  1) EXPORTACIÓN NORMAL (SIN FILTRO DE AÑO)
    // -----------------------------------------------------
    public Flux<DuenoExportDTO> buildExportData(Flux<Usuario> usuarioFlux) {
        return usuarioFlux.flatMap(this::buildDuenoWithMascotas);
    }

    private Mono<DuenoExportDTO> buildDuenoWithMascotas(Usuario usuario) {
        return mascotaRepository.findByUsuarioId(usuario.id())
                .flatMap(this::buildMascotaWithVacunas)
                .collectList()
                .map(mascotas -> createDuenoExportDTO(usuario, mascotas));
    }

    private Mono<MascotaExportDTO> buildMascotaWithVacunas(Mascota mascota) {
        return vacunaRepository.findByMascotaId(mascota.id())
                .map(vacuna -> new VacunaExportDTO(
                        vacuna.tipo(),
                        vacuna.fechaAplicacion()
                ))
                .collectList()
                .map(vacunas -> createMascotaExportDTO(mascota, vacunas));
    }


    // -----------------------------------------------------
    //  2) EXPORTACIÓN FILTRADA POR AÑO (SOLO PARA TODOS LOS DUEÑOS)
    // -----------------------------------------------------
    public Flux<DuenoExportDTO> buildExportDataByYear(Flux<Usuario> usuarioFlux, int anio) {
        return usuarioFlux.flatMap(usuario -> buildDuenoWithMascotasByYear(usuario, anio));
    }

    private Mono<DuenoExportDTO> buildDuenoWithMascotasByYear(Usuario usuario, int anio) {
        return mascotaRepository.findByUsuarioId(usuario.id())
                .flatMap(mascota -> buildMascotaWithVacunasByYear(mascota, anio))
                .collectList()
                .map(mascotas -> createDuenoExportDTO(usuario, mascotas));
    }

    private Mono<MascotaExportDTO> buildMascotaWithVacunasByYear(Mascota mascota, int anio) {
        return vacunaRepository.findByMascotaId(mascota.id())
                .filter(vacuna -> vacuna.fechaAplicacion() != null
                        && vacuna.fechaAplicacion().getYear() == anio)
                .map(vacuna -> new VacunaExportDTO(
                        vacuna.tipo(),
                        vacuna.fechaAplicacion()
                ))
                .collectList()
                .map(vacunas -> createMascotaExportDTO(mascota, vacunas));
    }


    // -----------------------------------------------------
    //   HELPERS: CREACIÓN DE DTOs
    // -----------------------------------------------------
    private DuenoExportDTO createDuenoExportDTO(Usuario usuario, List<MascotaExportDTO> mascotas) {
        return new DuenoExportDTO(
                usuario.id(),
                usuario.nombre(),
                usuario.dni(),
                usuario.direccion(),
                usuario.telefono(),
                mascotas
        );
    }

    private MascotaExportDTO createMascotaExportDTO(Mascota mascota, List<VacunaExportDTO> vacunas) {
        return new MascotaExportDTO(
                mascota.id(),
                mascota.nombre(),
                mascota.especie(),
                mascota.raza(),
                mascota.sexo().name(),
                mascota.color(),
                mascota.anios() + ", años " + mascota.meses() + " meses",
                vacunas
        );
    }
}
