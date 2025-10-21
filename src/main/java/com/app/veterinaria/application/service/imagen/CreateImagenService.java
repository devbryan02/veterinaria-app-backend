package com.app.veterinaria.application.service.imagen;

import com.app.veterinaria.application.mapper.ImagenDtoMapper;
import com.app.veterinaria.domain.model.Imagen;
import com.app.veterinaria.domain.repository.ImagenRepository;
import com.app.veterinaria.domain.repository.MascotaRepository;
import com.app.veterinaria.infrastructure.web.dto.request.ImagenNewRequest;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import com.app.veterinaria.shared.exception.mascota.MascotaNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateImagenService {

    private final ImagenUploadService imagenUploadService;
    private final ImagenDtoMapper imagenDtoMapper;
    private final ImagenRepository imagenRepository;
    private final MascotaRepository mascotaRepository;

    public Mono<OperationResponseStatus> execute(ImagenNewRequest request, FilePart file) {
        log.info("Iniciando proceso de creación de imagen para mascota: {}", request.mascotaId());

        UUID mascotaId = UUID.fromString(request.mascotaId());

        // Flujo: Validar mascota → Subir imagen → Guardar en BD
        return validateExistsMascota(mascotaId)
                .then(imagenUploadService.upload(file))
                .flatMap(url -> saveImagen(request, url))
                .map(saved -> {
                    log.info("Imagen guardada exitosamente con ID: {} para mascota: {}",
                            saved.getId(), mascotaId);
                    return OperationResponseStatus.ok("Imagen guardada correctamente");
                });
    }

    private Mono<Void> validateExistsMascota(UUID mascotaId) {
        log.debug("Verificando existencia de mascota con ID: {}", mascotaId);

        return mascotaRepository.existsById(mascotaId)
                .filter(exists -> exists)
                .switchIfEmpty(Mono.error(
                        new MascotaNotFoundException("Mascota no encontrada con id: " + mascotaId)
                ))
                .then();
    }

    private Mono<Imagen> saveImagen(ImagenNewRequest request, String url) {
        log.debug("Guardando imagen con URL: {}", url);

        Imagen imagen = imagenDtoMapper.toDomain(request);
        imagen.setUrl(url);
        imagen.setFechaSubida(LocalDate.now());

        return imagenRepository.save(imagen);
    }
}