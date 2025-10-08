package com.app.veterinaria.application.service.dueno;

import com.app.veterinaria.domain.repository.DuenoRepository;
import com.app.veterinaria.shared.exception.dueno.DuenoCreateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ValidationDuenoService {

    private final DuenoRepository duenoRepository;

    public Mono<Void> validateUniqueness(String DNI, String telefono, String correo){

        log.debug("Iniciando validación de unicidad - DNI: {}, Teléfono: {}, Correo: {}", DNI, telefono, correo);
        return Mono.zip(
                duenoRepository.existsByCorreo(correo),
                duenoRepository.existsByTelefono(telefono),
                duenoRepository.existsByDNI(DNI)
        )
                .flatMap(tuple -> {
                    List<String> errors = new ArrayList<>();
                    if(tuple.getT1()) {
                        log.error("DNI duplicado detectado: {}", DNI);
                        errors.add("El DNI ya está registrado");
                    }
                    if(tuple.getT2()) {
                        log.error("Telefono duplicado detectado: {}", telefono);
                        errors.add("El Telefono ya está registrado");
                    }
                    if(tuple.getT3()) {
                        log.error("Correo duplicado detectado: {}", correo);
                        errors.add("El correo ya está registrado");
                    }
                    if(!errors.isEmpty()) {
                        String errorMessage = String.join(", ", errors);
                        log.error("Validacio de unicidad falló ");
                        return Mono.error(new DuenoCreateException(errorMessage));
                    }
                    log.debug("Validad de unicidad exitosa");
                    return Mono.empty();
                });
    }

}
