package com.app.veterinaria.infrastructure.web.controller;

import com.app.veterinaria.application.service.vacuna.CreateVacunaService;
import com.app.veterinaria.infrastructure.web.dto.request.VacunaNewRequest;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/vacuna")
@RequiredArgsConstructor
public class VacunaController {

    private final CreateVacunaService createVacunaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OperationResponseStatus> createVacuna(@RequestBody @Validated VacunaNewRequest request){
        return createVacunaService.execute(request);
    }


}
