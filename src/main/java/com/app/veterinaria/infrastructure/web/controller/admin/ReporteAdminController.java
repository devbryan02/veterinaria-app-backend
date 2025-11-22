package com.app.veterinaria.infrastructure.web.controller.admin;

import com.app.veterinaria.application.service.dueno.GetDuenoService;
import com.app.veterinaria.application.service.reportes.ExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/admin/reporte")
@RequiredArgsConstructor
public class ReporteAdminController {

    private final ExportService exportService;
    private final GetDuenoService getDuenoService;

    @GetMapping("/dueno/{id}")
    public Mono<Void> exportByDueno(@PathVariable UUID id, ServerHttpResponse response) {
        return getDuenoService.findById(id)
                .flatMap(dueno -> {
                    String fileName = "registro_vacunacion_dueno_" + dueno.nombre() + ".xlsx";
                    response.setStatusCode(HttpStatus.OK);
                    response.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);
                    response.getHeaders().set(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=" + fileName);

                    return exportService.exportVacunasByDueno(id)
                            .flatMap(bytes -> {
                                DataBuffer buffer = new DefaultDataBufferFactory().wrap(bytes);
                                return response.writeWith(Mono.just(buffer));
                            });
                }).then();
    }

    @GetMapping("/todos")
    public Mono<Void> exportAll(
            ServerHttpResponse response,
            @RequestParam("anio") int anio
    ) {
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().setContentType(MediaType.APPLICATION_OCTET_STREAM);
        response.getHeaders().set(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=registro_vacunacion_completo_" + anio + ".xlsx"
        );

        return exportService.exportVacunasByYear(anio)
                .flatMap(bytes -> {
                    DataBuffer buffer = new DefaultDataBufferFactory().wrap(bytes);
                    return response.writeWith(Mono.just(buffer));
                })
                .then();
    }
}
