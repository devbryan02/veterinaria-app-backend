package com.app.veterinaria.infrastructure.web.controller.admin;

import com.app.veterinaria.application.service.auth.AuthenticationService;
import com.app.veterinaria.application.service.veterinaria.ToggleAccountVetService;
import com.app.veterinaria.application.service.veterinaria.GetVetsService;
import com.app.veterinaria.infrastructure.web.dto.details.VetInfoTable;
import com.app.veterinaria.infrastructure.web.dto.request.RegisterRequest;
import com.app.veterinaria.infrastructure.web.dto.response.AuthResponse;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/vet")
public class AdminController {

    private final ToggleAccountVetService toggleAccountVetService;
    private final GetVetsService getVetsService;
    private final AuthenticationService authenticationService;

    @GetMapping
    public Flux<VetInfoTable> findAllVets(){
        return getVetsService.findAllVets();
    }

    @GetMapping("/{usuarioId}")
    public Mono<VetInfoTable> findVetById(@PathVariable UUID usuarioId){
        return getVetsService.findVetById(usuarioId);
    }

    @PatchMapping("/{userId}/toggle-block")
    public Mono<OperationResponseStatus> blockUser(@PathVariable UUID userId, ServerWebExchange exchange){
        return toggleAccountVetService.toggle(userId, exchange);
    }

    @PostMapping("/register")
    public Mono<OperationResponseStatus>register(@Valid @RequestBody RegisterRequest registerRequest, ServerWebExchange exchange) {
        return authenticationService.register(registerRequest, exchange);
    }
}
