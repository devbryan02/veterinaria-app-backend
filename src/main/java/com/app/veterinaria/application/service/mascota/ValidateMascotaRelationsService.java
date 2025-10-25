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

        final String MESSAGE_VACUNA = "No es posible eliminar porque la mascota tiene registos de vacunas";
        final String MESSGE_IMAGE = "No es posible eliminar porque la mascota tiene imagenes asociadas";

        return Mono.zip(tieneVacuna, tieneImagen)
                .handle((tuple, sink)-> {

                    List<String> erros = new ArrayList<>();

                    if(tuple.getT1()) erros.add(MESSAGE_VACUNA);
                    if(tuple.getT2()) erros.add(MESSGE_IMAGE);

                    if(!erros.isEmpty()) {
                        String message = String.join(", ", erros);
                        log.warn("Error en las valicaciones");
                        sink.error(new MascotaWithRelationsException(message));
                    } else {
                        log.debug("Validacion exitosa");
                        sink.complete();
                    }
                });

    }
}
