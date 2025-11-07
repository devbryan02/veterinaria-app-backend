package com.app.veterinaria.infrastructure.web.controller;

import com.app.veterinaria.application.service.admin.CreateAdminService;
import com.app.veterinaria.application.service.dueno.*;
import com.app.veterinaria.application.service.mascota.CreateMascotaService;
import com.app.veterinaria.application.service.mascota.DeleteMascotaService;
import com.app.veterinaria.application.service.mascota.GetMascotaService;
import com.app.veterinaria.application.service.mascota.UpdateMascotaService;
import com.app.veterinaria.infrastructure.web.dto.details.DuenoDetails;
import com.app.veterinaria.infrastructure.web.dto.details.MascotaWithDuenoDetails;
import com.app.veterinaria.infrastructure.web.dto.request.*;
import com.app.veterinaria.infrastructure.web.dto.response.OperationResponseStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    // ================================
    // Autogestión del administrador
    // ================================
    private final CreateAdminService createAdminService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OperationResponseStatus> createAdmin(@RequestBody @Valid AdminNewRequest request) {
        return createAdminService.execute(request);
    }

    // ================================
    // Gestión de Dueños (por Admin)
    // ================================
    private final CreateDuenoService createDuenoService;
    private final FindDuenoService findDuenoService;
    private final ListDuenoService listDuenoService;
    private final SearchDuenoService searchDuenoService;
    private final UpdateDuenoIgnorePasswordAndLocationService updateDuenoService;
    private final DeleteDuenoService deleteDuenoService;

    // Crear un nuevo dueño
    @PostMapping("/dueno")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OperationResponseStatus> createDueno(@RequestBody @Valid DuenoNewRequest request) {
        return createDuenoService.execute(request);
    }

    // Listar todos los dueños
    @GetMapping("/dueno")
    @ResponseStatus(HttpStatus.OK)
    public Flux<DuenoDetails> findAllDueno() {
        return listDuenoService.execute();
    }

    // Buscar un dueño por término
    @GetMapping("/dueno/search")
    @ResponseStatus(HttpStatus.OK)
    public Flux<DuenoDetails> searchDueno(@RequestParam String term) {
        return searchDuenoService.execute(term);
    }

    // Buscar un dueño por ID
    @GetMapping("/dueno/{duenoId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<DuenoDetails> findDuenoById(@PathVariable UUID duenoId) {
        return findDuenoService.execute(duenoId);
    }

    // Actualizar dueño (sin contraseña ni ubicación)
    @PatchMapping("/dueno/{duenoId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<OperationResponseStatus> updateDuenoIgnorePasswordAndLocation(
            @RequestBody @Valid DuenoUpdateIgnorePasswordAndLocationRequest request,
            @PathVariable UUID duenoId) {
        return updateDuenoService.execute(request, duenoId);
    }

    // Eliminar un dueño
    @DeleteMapping("/dueno/{duenoId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<OperationResponseStatus> deleteDueno(@PathVariable UUID duenoId) {
        return deleteDuenoService.execute(duenoId);
    }

    // ================================
    // Gestión de Mascotas (por Admin)
    // ================================
    private final CreateMascotaService createMascotaService;
    private final GetMascotaService getMascotaService;
    private final UpdateMascotaService updateMascotaService;
    private final DeleteMascotaService deleteMascotaService;

    // Crear una nueva mascota
    @PostMapping("/mascota")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<OperationResponseStatus> createMascota(@RequestBody @Valid MascotaNewRequest request) {
        return createMascotaService.execute(request);
    }

    // Listar todas la mascota
    @GetMapping("/mascota")
    @ResponseStatus(HttpStatus.OK)
    public Flux<MascotaWithDuenoDetails> findAllMascota() {
        return getMascotaService.findAll();
    }

    // Buscar mascotas por un termino
    @GetMapping("/mascota/search")
    @ResponseStatus(HttpStatus.OK)
    public Flux<MascotaWithDuenoDetails> searchMascota(@RequestParam("term") String term){
        return getMascotaService.search(term);
    }

    // Filtrar mascotas por parametros opcionales
    @GetMapping("/mascota/filter")
    @ResponseStatus(HttpStatus.OK)
    public Flux<MascotaWithDuenoDetails> findAllMascotaFilter(
            @RequestParam("especie") String especie,
            @RequestParam("sexo") String sexo,
            @RequestParam("raza") String raza) {
        return getMascotaService.findByFilters(especie, sexo, raza);
    }

    // Obtener una mascota por ID
    @GetMapping("/mascota/{mascotaId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<MascotaWithDuenoDetails> findMascotaById(@PathVariable("mascotaId") UUID mascotaId) {
        return getMascotaService.findById(mascotaId);
    }

    // Actualiza un mascota por su ID
    @PatchMapping("/mascota/{mascotaId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<OperationResponseStatus> updateMascota(
            @RequestBody MascotaUpdateRequest request,
            @PathVariable UUID mascotaId
            ) {
        return updateMascotaService.execute(request, mascotaId);
    }

    // Eliminar una mascota con su ID
    @DeleteMapping("/mascota/{mascotaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<OperationResponseStatus> deleteMascota(@PathVariable UUID mascotaId) {
        return deleteMascotaService.execute(mascotaId);
    }

}
