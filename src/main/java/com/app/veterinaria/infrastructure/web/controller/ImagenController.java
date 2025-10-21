package com.app.veterinaria.infrastructure.web.controller;

import com.app.veterinaria.application.service.imagen.CreateImagenService;
import com.app.veterinaria.infrastructure.web.dto.request.ImagenNewRequest;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/imagen")
public class ImagenController {

    private final CreateImagenService createImagenService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OperationResponseStatus> createImagen(
            @RequestPart("file") FilePart file,
            @RequestPart("descripcion") String descripcion,
            @RequestPart("mascotaId") String mascotaId
            ){
        ImagenNewRequest request = new ImagenNewRequest(descripcion, mascotaId);
        return createImagenService.execute(request, file);
    }
}
