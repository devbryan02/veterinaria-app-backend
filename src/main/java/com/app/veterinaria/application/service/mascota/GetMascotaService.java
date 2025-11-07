package com.app.veterinaria.application.service.mascota;

import com.app.veterinaria.application.respository.MascotaQueryRepository;
import com.app.veterinaria.infrastructure.web.dto.details.MascotaWithDuenoDetails;
import com.app.veterinaria.shared.exception.mascota.MascotaNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
public class GetMascotaService {

    private final MascotaQueryRepository mascotaQueryRepository;

    public Mono<MascotaWithDuenoDetails> findById(UUID mascotaId) {
        return Mono.defer(() -> {
            if (mascotaId == null) {
                log.warn("Intento de búsqueda con mascotaId nulo");
                return Mono.error(new IllegalArgumentException("El ID de la mascota no puede ser nulo"));
            }

            log.debug("Buscando mascota con ID: {}", mascotaId);
            return mascotaQueryRepository.findById(mascotaId)
                    .switchIfEmpty(Mono.error(
                            new MascotaNotFoundException("Mascota con ID " + mascotaId + " no encontrada")
                    ))
                    .doOnSuccess(mascota -> log.debug("Mascota encontrada: {}", mascotaId));
        });
    }

    public Flux<MascotaWithDuenoDetails> findAll() {
        log.debug("Obteniendo todas las mascotas");
        return mascotaQueryRepository.findAllWithDueno()
                .doOnComplete(() -> log.debug("Consulta de todas las mascotas completada"));
    }

    public Flux<MascotaWithDuenoDetails> search(String term) {
        return Flux.defer(() -> {
            if (!StringUtils.hasText(term)) {
                log.warn("Término de búsqueda vacío o nulo");
                return Flux.error(new IllegalArgumentException(
                        "El término de búsqueda no puede estar vacío"
                ));
            }

            String cleanTerm = term.trim();
            if (cleanTerm.length() < 2) {
                log.warn("Término de búsqueda muy corto: {}", cleanTerm);
                return Flux.error(new IllegalArgumentException(
                        "El término de búsqueda debe tener al menos 2 caracteres"
                ));
            }

            log.debug("Buscando mascotas con término: {}", cleanTerm);
            return mascotaQueryRepository.search(cleanTerm)
                    .doOnComplete(() -> log.debug("Búsqueda completada para: {}", cleanTerm));
        });
    }

    public Flux<MascotaWithDuenoDetails> findByFilters(String especie, String sexo, String raza) {
        return Flux.defer(() -> {
            // Validar que al menos un filtro esté presente
            if (!StringUtils.hasText(especie) &&
                    !StringUtils.hasText(sexo) &&
                    !StringUtils.hasText(raza)) {
                log.warn("Intento de filtrado sin ningún parámetro válido");
                return Flux.error(new IllegalArgumentException(
                        "Debe proporcionar al menos un filtro válido (especie, sexo o raza)"
                ));
            }

            // Normalizar valores (trim y null si están vacíos)
            String especieNorm = normalizeParam(especie);
            String sexoNorm = normalizeParam(sexo);
            String razaNorm = normalizeParam(raza);

            log.debug("Filtrando mascotas - Especie: {}, Sexo: {}, Raza: {}",
                    especieNorm, sexoNorm, razaNorm);

            return mascotaQueryRepository.findByFilters(especieNorm, sexoNorm, razaNorm)
                    .doOnComplete(() -> log.debug("Filtrado completado"));
        });
    }

    // Métodos auxiliares para validación
    private String normalizeParam(String param) {
        if (!StringUtils.hasText(param)) {
            return null;
        }
        String normalized = param.trim().toUpperCase();
        return normalized.isEmpty() ? null : normalized;
    }

}