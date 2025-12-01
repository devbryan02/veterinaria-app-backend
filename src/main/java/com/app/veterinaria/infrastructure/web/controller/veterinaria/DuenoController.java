package com.app.veterinaria.infrastructure.web.controller.veterinaria;

import com.app.veterinaria.application.service.dueno.*;
import com.app.veterinaria.infrastructure.web.dto.details.DuenoFullDetails;
import com.app.veterinaria.infrastructure.web.dto.details.DuenoWithCantMascotaDetails;
import com.app.veterinaria.infrastructure.web.dto.request.DuenoNewRequest;
import com.app.veterinaria.infrastructure.web.dto.request.DuenoUpdateRequest;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/veterinaria/dueno")
@RequiredArgsConstructor
public class DuenoController {

    private final CreateDuenoService createDuenoService;
    private final GetDuenoService getDuenoService;
    private final UpdateDuenoService updateDuenoService;
    private final DeleteDuenoService deleteDuenoService;
    private final GetDuenoDetailsService getDuenoDetailsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OperationResponseStatus> create(@RequestBody @Valid DuenoNewRequest request) {
        return createDuenoService.execute(request);
    }

    @GetMapping("/details/{duenoId}")
    public Mono<DuenoFullDetails> getDetailsDueno(@PathVariable UUID duenoId) {
        return getDuenoDetailsService.execute(duenoId);
    }

    @GetMapping
    public Flux<DuenoWithCantMascotaDetails> findAll() {
        return getDuenoService.findAllDuenos();
    }

    @GetMapping("/search")
    public Flux<DuenoWithCantMascotaDetails> search(@RequestParam String term) {
        return getDuenoService.searchByTerm(term);
    }

    @GetMapping("/{id}")
    public Mono<DuenoWithCantMascotaDetails> findById(@PathVariable UUID id) {
        return getDuenoService.findById(id);
    }

    @PutMapping("/{id}")
    public Mono<OperationResponseStatus> update(
            @RequestBody @Valid DuenoUpdateRequest request,
            @PathVariable UUID id,
            ServerWebExchange exchange
    ) {
        return updateDuenoService.execute(request, id, exchange);
    }

    @DeleteMapping("/{id}")
    public Mono<OperationResponseStatus> delete(@PathVariable UUID id, ServerWebExchange exchange) {
        return deleteDuenoService.execute(id, exchange);
    }
}
