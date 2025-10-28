package com.app.veterinaria.application.service.dueno;

import com.app.veterinaria.application.mapper.DuenoDtoMapper;
import com.app.veterinaria.domain.repository.DuenoRepository;
import com.app.veterinaria.domain.repository.MascotaRepository;
import com.app.veterinaria.infrastructure.web.dto.details.DuenoDetails;
import com.app.veterinaria.shared.exception.mascota.MascotaNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListDuenoService {

    private final DuenoRepository duenoRepository;
    private final DuenoDtoMapper duenoDtoMapper;
    private final MascotaRepository mascotaRepository;

    public Flux<DuenoDetails> execute(){
        return duenoRepository.findAll()
                .flatMap(dueno ->
                        mascotaRepository.findByDuenoId(dueno.getId())
                                .collectList()
                                .switchIfEmpty(Mono.error(new MascotaNotFoundException("No se encontraron mascotas registradas")))
                                .doOnNext(mascotas -> log.info("Se encontraron {} mascota(s) para el dueño {}", mascotas.size(), dueno.getNombre()))
                                .map(mascota ->{
                                    dueno.setMascotas(mascota);
                                    return duenoDtoMapper.toDetails(dueno);
                                })
                );
    }
}
