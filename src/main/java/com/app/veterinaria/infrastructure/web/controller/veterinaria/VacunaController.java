package com.app.veterinaria.infrastructure.web.controller.veterinaria;

import com.app.veterinaria.application.service.vacuna.CreateVacunaService;
import com.app.veterinaria.application.service.vacuna.DeleteVacunaService;
import com.app.veterinaria.application.service.vacuna.GetVacunaService;
import com.app.veterinaria.application.service.vacuna.UpdateVacunaService;
import com.app.veterinaria.infrastructure.web.dto.details.VacunaWithMascotaDetails;
import com.app.veterinaria.infrastructure.web.dto.request.VacunaNewRequest;
import com.app.veterinaria.infrastructure.web.dto.request.VacunaUpdateRequest;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/veterinaria/vacuna")
@RequiredArgsConstructor
public class VacunaController {

    private final CreateVacunaService createVacunaService;
    private final GetVacunaService getVacunaService;
    private final UpdateVacunaService updateVacunaService;
    private final DeleteVacunaService deleteVacunaService;

    @PostMapping
    public Mono<OperationResponseStatus> create(@RequestBody @Valid VacunaNewRequest request) {
        return createVacunaService.execute(request);
    }

    @GetMapping("/{id}")
    public Mono<VacunaWithMascotaDetails> findById(@PathVariable UUID id) {
        return getVacunaService.findById(id);
    }

    @GetMapping
    public Flux<VacunaWithMascotaDetails> findAll() {
        return getVacunaService.findAll();
    }

    @GetMapping("/filter")
    @ResponseStatus(HttpStatus.OK)
    public Flux<VacunaWithMascotaDetails> filter(@RequestParam("tipo") String tipo) {
        return getVacunaService.filterByTipo(tipo);
    }

    @GetMapping("/date-range")
    public Flux<VacunaWithMascotaDetails> dateRange(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        return getVacunaService.findVacunasByDate(startDate, endDate);
    }

    @DeleteMapping("/{vacunaId}")
    public Mono<OperationResponseStatus> delete(
            @PathVariable UUID vacunaId,
            ServerWebExchange exchange){
        return deleteVacunaService.execute(vacunaId, exchange);
    }

    @PutMapping("/{vacunaId}")
    public Mono<OperationResponseStatus> update(
            @RequestBody @Valid VacunaUpdateRequest request,
            @PathVariable UUID vacunaId,
            ServerWebExchange exchange){
        return updateVacunaService.execute(request, vacunaId, exchange);
    }
}
