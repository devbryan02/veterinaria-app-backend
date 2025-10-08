package com.app.veterinaria.infrastructure.web.controller;

import com.app.veterinaria.application.service.dueno.CreateDuenoService;
import com.app.veterinaria.infrastructure.web.dto.request.DuenoNewRequest;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/dueno")
@RequiredArgsConstructor
public class DuenoController {

    private final CreateDuenoService createDuenoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OperationResponseStatus> createDueno(@RequestBody @Valid DuenoNewRequest request) {
        return createDuenoService.execute(request);
    }
}
