package com.app.veterinaria.infrastructure.web.controller.veterinaria;

import com.app.veterinaria.application.service.imagen.CreateImagenService;
import com.app.veterinaria.infrastructure.web.dto.request.ImagenNewRequest;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/veterinaria/mascota/imagen")
@RequiredArgsConstructor
public class ImagenController {

    private final CreateImagenService createImagenService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<OperationResponseStatus> upload(
            @RequestPart("file") FilePart file,
            @RequestPart("mascotaId") String mascotaId,
            @RequestPart("descripcion") String descripcion
    ) {
        ImagenNewRequest request = new ImagenNewRequest(mascotaId, descripcion);
        return createImagenService.execute(request, file);
    }
}

