package com.app.veterinaria.infrastructure.web.controller.veterinaria;

import com.app.veterinaria.application.service.mascota.*;
import com.app.veterinaria.infrastructure.web.dto.details.MascotaFullDetails;
import com.app.veterinaria.infrastructure.web.dto.details.MascotaWithDuenoDetails;
import com.app.veterinaria.infrastructure.web.dto.request.MascotaNewRequest;
import com.app.veterinaria.infrastructure.web.dto.request.MascotaUpdateRequest;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/veterinaria/mascota")
@RequiredArgsConstructor
public class MascotaController {

    private final CreateMascotaService createMascotaService;
    private final GetMascotaService getMascotaService;
    private final UpdateMascotaService updateMascotaService;
    private final DeleteMascotaService deleteMascotaService;
    private final GetMascotaDetailsService getMascotaDetailsService;

    @PostMapping
    public Mono<OperationResponseStatus> create(@RequestBody @Valid MascotaNewRequest request) {
        return createMascotaService.execute(request);
    }

    @GetMapping
    public Flux<MascotaWithDuenoDetails> findAll() {
        return getMascotaService.findAllWithLimit();
    }

    @GetMapping("/details/{mascotaId}")
    public Mono<MascotaFullDetails> details(@PathVariable UUID mascotaId) {
        return getMascotaDetailsService.execute(mascotaId);
    }

    @GetMapping("/search")
    public Flux<MascotaWithDuenoDetails> search(@RequestParam String term) {
        return getMascotaService.search(term);
    }

    @GetMapping("/filter")
    public Flux<MascotaWithDuenoDetails> filter(
            @RequestParam(required = false) String especie,
            @RequestParam(required = false) String sexo,
            @RequestParam(required = false) String raza
    ) {
        return getMascotaService.findByFilters(especie, sexo, raza);
    }

    @GetMapping("/{id}")
    public Mono<MascotaWithDuenoDetails> findById(@PathVariable UUID id) {
        return getMascotaService.findById(id);
    }

    @PutMapping("/{id}")
    public Mono<OperationResponseStatus> update(
            @RequestBody @Valid MascotaUpdateRequest request,
            @PathVariable UUID id,
            ServerWebExchange exchange
    ) {
        return updateMascotaService.execute(request, id, exchange);
    }

    @DeleteMapping("/{id}")
    public Mono<OperationResponseStatus> delete(@PathVariable UUID id, ServerWebExchange exchange) {
        return deleteMascotaService.execute(id, exchange);
    }
}
