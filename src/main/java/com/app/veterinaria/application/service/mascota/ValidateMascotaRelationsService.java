package com.app.veterinaria.application.service.mascota;

import com.app.veterinaria.domain.repository.ImagenRepository;
import com.app.veterinaria.domain.repository.VacunaRepository;
import com.app.veterinaria.shared.exception.mascota.MascotaWithRelationsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class ValidateMascotaRelationsService {

    private final ImagenRepository imagenRepository;
    private final VacunaRepository vacunaRepository;

    public Mono<Void> execute(UUID mascotaId) {
        Mono<Boolean> tieneVacuna = vacunaRepository.existsByMascotaId(mascotaId);
        Mono<Boolean> tieneImagen = imagenRepository.existsByMascotaId(mascotaId);

        final String MESSAGE_VACUNA = "No es posible eliminar porque la mascota tiene registros de vacunas";
        final String MESSAGE_IMAGE = "No es posible eliminar porque la mascota tiene imágenes asociadas";

        return Mono.zip(tieneVacuna, tieneImagen)
                .flatMap(tuple -> {
                    List<String> errores = new ArrayList<>();

                    if (tuple.getT1()) errores.add(MESSAGE_VACUNA);
                    if (tuple.getT2()) errores.add(MESSAGE_IMAGE);

                    if (!errores.isEmpty()) {
                        String message = String.join(", ", errores);
                        log.warn("Error en las validaciones: {}", message);
                        return Mono.error(new MascotaWithRelationsException(message));
                    }

                    log.debug("Validación exitosa para mascota {}", mascotaId);
                    return Mono.empty();
                });
    }

}
