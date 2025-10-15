package com.app.veterinaria.infrastructure.web.controller;

import com.app.veterinaria.application.service.mascota.CreateMascotaService;
import com.app.veterinaria.application.service.mascota.FindMascotaDetails;
import com.app.veterinaria.infrastructure.web.dto.details.MascotaDetails;
import com.app.veterinaria.infrastructure.web.dto.request.MascotaNewRequest;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/mascota")
@RequiredArgsConstructor
public class MascotaController {

    private final CreateMascotaService createMascotaService;
    private final FindMascotaDetails findMascotaDetails;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OperationResponseStatus> createMascota(@RequestBody @Valid MascotaNewRequest request){
        return createMascotaService.execute(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<MascotaDetails> findAllMascota(){
        return findMascotaDetails.execute();
    }

}
