package com.app.veterinaria.infrastructure.web.controller;

import com.app.veterinaria.application.service.mascota.*;
import com.app.veterinaria.infrastructure.web.dto.details.MascotaWithDuenoDetails;
import com.app.veterinaria.infrastructure.web.dto.request.MascotaNewRequest;
import com.app.veterinaria.infrastructure.web.dto.request.MascotaUpdateRequest;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/mascota")
@RequiredArgsConstructor
public class MascotaController {

    private final CreateMascotaService createMascotaService;
    private final ListMascotasService listMascotasService;
    private final DeleteMascotaService deleteMascotaService;
    private final FindMascotaService findMascotaService;
    private final UpdateMascotaService updateMascotaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OperationResponseStatus> createMascota(@RequestBody @Valid MascotaNewRequest request){
        return createMascotaService.execute(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<MascotaWithDuenoDetails> findAllMascota(){
        return listMascotasService.execute();
    }

    @DeleteMapping("/{mascotaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<OperationResponseStatus> deleteMascota(@PathVariable UUID mascotaId){
        return deleteMascotaService.execute(mascotaId);
    }

    @GetMapping("/{mascotaId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<MascotaWithDuenoDetails> findMascotaById(@PathVariable UUID mascotaId){
        return findMascotaService.execute(mascotaId);
    }

    @PatchMapping("/{mascotaId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<OperationResponseStatus> updateMascota(@RequestBody MascotaUpdateRequest request, @PathVariable UUID mascotaId){
        return updateMascotaService.execute(request, mascotaId);
    }
}
