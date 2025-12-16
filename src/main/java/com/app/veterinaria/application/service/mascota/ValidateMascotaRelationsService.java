package com.app.veterinaria.application.service.mascota;

import com.app.veterinaria.domain.repository.VacunaRepository;
import com.app.veterinaria.shared.exception.mascota.MascotaWithRelationsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ValidateMascotaRelationsService {

    private final VacunaRepository vacunaRepository;

    /**
     * Valida solo relaciones críticas que NO deben eliminarse en cascada
     * Las imágenes se eliminan automáticamente antes de eliminar la mascota
     */
    public Mono<Void> execute(UUID mascotaId) {
        return vacunaRepository.existsByMascotaId(mascotaId)
                .flatMap(tieneVacunas -> {
                    if (tieneVacunas) {
                        log.warn("Intento de eliminar mascota [{}] con vacunas registradas", mascotaId);
                        return Mono.error(new MascotaWithRelationsException(
                                "No es posible eliminar porque la mascota tiene registros de vacunas"));
                    }

                    log.debug("✓ Validación exitosa para mascota [{}]", mascotaId);
                    return Mono.empty();
                });
    }
}