package com.app.veterinaria.application.service.dueno;

import com.app.veterinaria.domain.repository.UsuarioRepository;
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
public class ValidationUniquesUsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Mono<Void> validateUniqueness(String DNI, String telefono, String correo){
        log.debug("Validando unicidad - DNI: {}, Tel: {}, Email: {}", DNI, telefono, correo);

        return Mono.zip(
                        usuarioRepository.existsByDNI(DNI),
                        usuarioRepository.existsByTelefono(telefono),
                        usuarioRepository.existsByCorreo(correo)
                )
                .handle((tuple, sink) -> {
                    List<String> errors = new ArrayList<>();

                    if(tuple.getT1()) errors.add("El DNI ya está registrado");
                    if(tuple.getT2()) errors.add("El teléfono ya está registrado");
                    if(tuple.getT3()) errors.add("El correo ya está registrado");

                    if(!errors.isEmpty()) {
                        String message = String.join(", ", errors);
                        log.warn("Validación fallida: {}", message);
                        sink.error(new DuenoCreateException(message));
                    } else {
                        log.debug("Validación exitosa");
                        sink.complete();
                    }
                });
    }

}
