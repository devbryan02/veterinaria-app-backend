package com.app.veterinaria.application.service.imagen;

import com.app.veterinaria.domain.repository.ImagenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeleteImageService {

    private final ImagenRepository imagenRepository;
    private final DeleteCoudinaryImage deleteCoudinaryImage;

    /**
     * Elimina todas las imágenes asociadas a una mascota
     * 1. Elimina de Cloudinary (por publicId)
     * 2. Elimina de la BD
     */
    public Mono<Void> execute(UUID mascotaId) {

        return imagenRepository.findByMascotaId(mascotaId)
                .flatMap(imagen ->
                        deleteCoudinaryImage.deleteByPublicId(imagen.publicId())
                                .thenReturn(imagen)
                )
                .collectList()
                .flatMap(imagenes -> {

                    if (imagenes.isEmpty()) {
                        log.debug("No hay imágenes para eliminar de mascota [{}]", mascotaId);
                        return Mono.empty();
                    }

                    return imagenRepository.deleteByMascotaId(mascotaId)
                            .doOnSuccess(v ->
                                    log.info("✓ {} imagen(es) eliminadas para mascota [{}]",
                                            imagenes.size(), mascotaId)
                            );
                })
                .doOnError(e ->
                        log.error("✗ Error al eliminar imágenes de mascota [{}]", mascotaId, e)
                )
                .then();
    }
}
