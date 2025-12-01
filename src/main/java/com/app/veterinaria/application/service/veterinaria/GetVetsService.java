package com.app.veterinaria.application.service.veterinaria;

import com.app.veterinaria.application.mapper.response.VetResponseMapper;
import com.app.veterinaria.domain.repository.UsuarioRepository;
import com.app.veterinaria.infrastructure.web.dto.details.VetInfoTable;
import com.app.veterinaria.shared.exception.user.UsuarioNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetVetsService {

    private final UsuarioRepository usuarioRepository;
    private final VetResponseMapper mapper;

    public Flux<VetInfoTable> findAllVets(){
        return usuarioRepository.findAllVets()
                .doOnSubscribe(sub -> log.info("Buscando todos los veterinarios"))
                .map(mapper::toDetails)
                .doOnComplete(() -> log.info("Busqueda completada"));
    }

    public Mono<VetInfoTable> findVetById(UUID usuarioId){
        return usuarioRepository.findById(usuarioId)
                .switchIfEmpty(Mono.error(new UsuarioNotFoundException("Veterinario no encontrado")))
                .map(mapper::toDetails);
    }

}
