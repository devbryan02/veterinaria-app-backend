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

}
