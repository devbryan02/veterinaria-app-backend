package com.app.veterinaria.infrastructure.web.controller;

import com.app.veterinaria.application.service.veterinaria.VetCreateService;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/admin/veterinaria")
@RequiredArgsConstructor
public class AdminController {

    private final VetCreateService vetCreateService;

    @PostMapping
    public Mono<OperationResponseStatus> createVet(@RequestBody @Valid VeterinariaNewRequest request) {
        return vetCreateService.execute(request);
    }

}
