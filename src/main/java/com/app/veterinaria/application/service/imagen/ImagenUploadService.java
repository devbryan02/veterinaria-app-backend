package com.app.veterinaria.application.service.imagen;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class ImagenUploadService {

    private static final String UPLOAD_FOLDER = "images_mascotas";
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5MB
    private static final int MAX_WIDTH = 1024; // límite de ancho
    private static final int MAX_HEIGHT = 1024; // límite de alto

    private static final List<String> ALLOWED_TYPES =
            List.of("image/jpeg", "image/png", "image/jpg", "image/webp");

    private final Cloudinary cloudinary;

    public ImagenUploadService(
            @Value("${cloudinary.cloud_name}") String cloudName,
            @Value("${cloudinary.api_key}") String apiKey,
            @Value("${cloudinary.api_secret}") String apiSecret
    ) {
        this.cloudinary = new Cloudinary(Map.of(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
        log.info("Cloudinary inicializado correctamente");
    }

    public Mono<String> upload(FilePart filePart) {
        log.info("Iniciando upload: {}", filePart.filename());
        return validateFile(filePart)
                .flatMap(this::convertToBytes)
                .flatMap(this::uploadToCloudinary)
                .doOnSuccess(url -> log.info("Imagen subida y optimizada: {}", url))
                .doOnError(e -> log.error("Error al subir imagen", e));
    }

    // ================= VALIDACIONES =================

    private Mono<FilePart> validateFile(FilePart filePart) {
        String filename = filePart.filename();
        String contentType = filePart.headers().getContentType() != null
                ? Objects.requireNonNull(filePart.headers().getContentType()).toString().toLowerCase()
                : null;

        if (filename.isBlank()) {
            return Mono.error(new IllegalArgumentException("El archivo debe tener un nombre válido"));
        }

        if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
            return Mono.error(new IllegalArgumentException(
                    "Tipo de archivo no permitido (JPEG, PNG, JPG, WEBP)"
            ));
        }

        log.info("Archivo validado: {} [{}]", filename, contentType);
        return Mono.just(filePart);
    }

    // ================= CONVERSIÓN A BYTES =================

    private Mono<byte[]> convertToBytes(FilePart filePart) {
        return filePart.content()
                .publishOn(Schedulers.boundedElastic())
                .reduce(new ByteArrayOutputStream(), (baos, dataBuffer) -> {
                    try {
                        baos.write(dataBuffer.asInputStream().readAllBytes());
                        return baos;
                    } catch (IOException e) {
                        throw new RuntimeException("Error al leer el archivo", e);
                    }
                })
                .map(ByteArrayOutputStream::toByteArray)
                .doOnNext(bytes -> {
                    if (bytes.length > MAX_FILE_SIZE) {
                        throw new IllegalArgumentException("La imagen excede los 5MB permitidos");
                    }
                });
    }

    // ================= UPLOAD OPTIMIZADO =================

    private Mono<String> uploadToCloudinary(byte[] fileBytes) {
        return Mono.fromCallable(() -> {

            // Transformación FUERTEMENTE optimizada
            Transformation<?> transformation = new Transformation<>()
                    .width(MAX_WIDTH)
                    .height(MAX_HEIGHT)
                    .crop("limit")                 // no deforma
                    .quality("auto:good")          // compresión inteligente
                    .fetchFormat("auto")           // WebP / AVIF
                    .flags("strip_metadata");      // elimina EXIF/GPS

            var result = cloudinary.uploader().upload(
                    fileBytes,
                    Map.of(
                            "folder", UPLOAD_FOLDER,
                            "resource_type", "image",
                            "transformation", transformation
                    )
            );

            return result.get("secure_url").toString();
        }).subscribeOn(Schedulers.boundedElastic());
    }
}
