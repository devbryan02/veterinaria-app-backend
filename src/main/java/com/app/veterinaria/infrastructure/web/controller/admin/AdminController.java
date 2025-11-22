package com.app.veterinaria.infrastructure.web.controller.admin;

import com.app.veterinaria.application.service.admin.CreateAdminService;
import com.app.veterinaria.infrastructure.web.dto.request.AdminNewRequest;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CreateAdminService createAdminService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OperationResponseStatus> createAdmin(@RequestBody @Valid AdminNewRequest request) {
        return createAdminService.execute(request);
    }
}
