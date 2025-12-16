package com.app.veterinaria.application.service.imagen;

import com.app.veterinaria.application.mapper.request.ImagenRequestMapper;
import com.app.veterinaria.domain.model.Imagen;
import com.app.veterinaria.domain.repository.ImagenRepository;
import com.app.veterinaria.domain.repository.MascotaRepository;
import com.app.veterinaria.domain.valueobject.CloudinaryUploadResult;
import com.app.veterinaria.infrastructure.web.dto.request.ImagenNewRequest;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import com.app.veterinaria.shared.exception.mascota.MascotaNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateImagenService {

    private final ImagenUploadService imagenUploadService;
    private final ImagenRequestMapper mapper;
    private final ImagenRepository imagenRepository;
    private final MascotaRepository mascotaRepository;

    public Mono<OperationResponseStatus> execute(ImagenNewRequest request, FilePart file) {

        UUID mascotaId = UUID.fromString(request.mascotaId());

        return validateExistsMascota(mascotaId)
                .then(imagenUploadService.upload(file))
                .flatMap(result -> saveImagen(request, result))
                .map(saved -> OperationResponseStatus.ok("Imagen guardada correctamente"));
    }

    private Mono<Void> validateExistsMascota(UUID mascotaId) {
        return mascotaRepository.existsById(mascotaId)
                .filter(Boolean::booleanValue)
                .switchIfEmpty(Mono.error(
                        new MascotaNotFoundException("Mascota no encontrada: " + mascotaId)
                ))
                .then();
    }

    private Mono<Imagen> saveImagen(
            ImagenNewRequest request,
            CloudinaryUploadResult result
    ) {

        Imagen imagen = mapper.toDomain(
                request,
                result.url(),
                result.publicId()
        );

        return imagenRepository.save(imagen);
    }
}
