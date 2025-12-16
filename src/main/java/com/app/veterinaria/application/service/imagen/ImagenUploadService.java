package com.app.veterinaria.application.service.imagen;

import com.app.veterinaria.domain.valueobject.CloudinaryUploadResult;
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
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024;
    private static final int MAX_WIDTH = 1024;
    private static final int MAX_HEIGHT = 1024;

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
    }

    public Mono<CloudinaryUploadResult> upload(FilePart filePart) {
        return validateFile(filePart)
                .flatMap(this::convertToBytes)
                .flatMap(this::uploadToCloudinary)
                .doOnSuccess(r ->
                        log.info("Imagen subida: publicId={}, url={}", r.publicId(), r.url())
                );
    }

    // ================= VALIDACIONES =================

    private Mono<FilePart> validateFile(FilePart filePart) {
        String filename = filePart.filename();
        String contentType = filePart.headers().getContentType() != null
                ? filePart.headers().getContentType().toString().toLowerCase()
                : null;

        if (filename.isBlank()) {
            return Mono.error(new IllegalArgumentException("Nombre de archivo inválido"));
        }

        if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
            return Mono.error(new IllegalArgumentException("Tipo de imagen no permitido"));
        }

        return Mono.just(filePart);
    }

    // ================= BYTES =================

    private Mono<byte[]> convertToBytes(FilePart filePart) {
        return filePart.content()
                .publishOn(Schedulers.boundedElastic())
                .reduce(new ByteArrayOutputStream(), (baos, buffer) -> {
                    try {
                        baos.write(buffer.asInputStream().readAllBytes());
                        return baos;
                    } catch (Exception e) {
                        throw new RuntimeException("Error leyendo archivo", e);
                    }
                })
                .map(ByteArrayOutputStream::toByteArray)
                .doOnNext(bytes -> {
                    if (bytes.length > MAX_FILE_SIZE) {
                        throw new IllegalArgumentException("La imagen supera 5MB");
                    }
                });
    }

    // ================= CLOUDINARY =================

    private Mono<CloudinaryUploadResult> uploadToCloudinary(byte[] bytes) {
        return Mono.fromCallable(() -> {

            Transformation<?> transformation = new Transformation<>()
                    .width(MAX_WIDTH)
                    .height(MAX_HEIGHT)
                    .crop("limit")
                    .quality("auto:good")
                    .fetchFormat("auto");

            Map<String, Object> result = cloudinary.uploader().upload(
                    bytes,
                    Map.of(
                            "folder", UPLOAD_FOLDER,
                            "resource_type", "image",
                            "transformation", transformation
                    )
            );

            return new CloudinaryUploadResult(
                    result.get("secure_url").toString(),
                    result.get("public_id").toString()
            );

        }).subscribeOn(Schedulers.boundedElastic());
    }
}

