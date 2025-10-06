package com.app.veterinaria.infrastructure.web.controller;

import com.app.veterinaria.application.service.admin.CreateAdminService;
import com.app.veterinaria.infrastructure.web.dto.request.AdminNewRequest;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CreateAdminService createAdminService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OperationResponseStatus> createAdmin(AdminNewRequest request){
        return createAdminService.execute(request);
    }


}
