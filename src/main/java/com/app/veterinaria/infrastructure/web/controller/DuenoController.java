package com.app.veterinaria.infrastructure.web.controller;

import com.app.veterinaria.application.service.dueno.*;
import com.app.veterinaria.infrastructure.web.dto.details.DuenoDetails;
import com.app.veterinaria.infrastructure.web.dto.request.DuenoNewRequest;
import com.app.veterinaria.infrastructure.web.dto.request.DuenoUpdateRequest;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/dueno")
@RequiredArgsConstructor
public class DuenoController {

    private final CreateDuenoService createDuenoService;
    private final FindDuenoService findDuenoService;
    private final ListDuenoService listDuenoService;
    private final DeleteDuenoService deleteDuenoService;
    private final UpdateDuenoService updateDuenoService;
    private final SearchDuenoService searchDuenoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OperationResponseStatus> createDueno(@RequestBody @Valid DuenoNewRequest request) {
        return createDuenoService.execute(request);
    }

    @GetMapping("/{duenoId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<DuenoDetails> findDuenoById(@PathVariable UUID duenoId) {
        return findDuenoService.execute(duenoId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<DuenoDetails> findAllDueno(){
        return listDuenoService.execute();
    }

    @DeleteMapping("/{duenoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<OperationResponseStatus> deleteById(@PathVariable UUID duenoId) {
        return deleteDuenoService.execute(duenoId);
    }

    @PatchMapping("/{duenoId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<OperationResponseStatus> updateDueno(@RequestBody DuenoUpdateRequest request,  @PathVariable UUID duenoId) {
        return  updateDuenoService.execute(request,duenoId);
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Flux<DuenoDetails> searchDueno(@RequestParam String term) {
        return searchDuenoService.execute(term);
    }
}
