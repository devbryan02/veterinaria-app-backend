package com.app.veterinaria.application.service.imagen;

import com.cloudinary.Cloudinary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Map;

@Service
@Slf4j
public class DeleteCoudinaryImage {

    private final Cloudinary cloudinary;

    public DeleteCoudinaryImage(
            @Value("${cloudinary.cloud_name}") String cloudName,
            @Value("${cloudinary.api_key}") String apiKey,
            @Value("${cloudinary.api_secret}") String apiSecret
    ) {
        this.cloudinary = new Cloudinary(Map.of(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
    }

    /**
     * Elimina una imagen de Cloudinary usando su publicId
     */
    public Mono<Void> deleteByPublicId(String publicId) {

        if (publicId == null || publicId.isBlank()) {
            log.warn("publicId vacío, se omite eliminación en Cloudinary");
            return Mono.empty();
        }

        return Mono.fromCallable(() -> cloudinary.uploader().destroy(
                        publicId,
                        Map.of(
                                "resource_type", "image",
                                "invalidate", true
                        )
                ))
                .subscribeOn(Schedulers.boundedElastic())
                .doOnSuccess(result ->
                        log.info("✓ Imagen eliminada de Cloudinary [{}]", publicId)
                )
                .onErrorResume(e -> {
                    log.warn("No se pudo eliminar de Cloudinary [{}]: {}", publicId, e.getMessage());
                    return Mono.empty();
                })
                .then();
    }
}
