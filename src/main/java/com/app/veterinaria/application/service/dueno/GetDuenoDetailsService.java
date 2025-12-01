package com.app.veterinaria.application.service.dueno;

import com.app.veterinaria.application.mapper.response.DuenoResponseMapper;
import com.app.veterinaria.domain.model.Usuario;
import com.app.veterinaria.domain.repository.MascotaRepository;
import com.app.veterinaria.domain.repository.UsuarioRepository;
import com.app.veterinaria.infrastructure.web.dto.details.DuenoFullDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetDuenoDetailsService {

    private final UsuarioRepository usuarioRepository;
    private final MascotaRepository mascotaRepository;
    private final DuenoResponseMapper mapper;


    public Mono<DuenoFullDetails> execute(UUID usuarioId) {

        return usuarioRepository.findById(usuarioId)
                .switchIfEmpty(Mono.error(new RuntimeException("Dueño no encontrado")))
                .flatMap(this::loadRelations);
    }

    private Mono<DuenoFullDetails> loadRelations(Usuario usuario) {
        return mascotaRepository.findByUsuarioId(usuario.id())
                .map(mapper::toMascotaDetails)
                .collectList()
                .map(mapper::toResumen)
                .map(resumen -> mapper.toDetails(usuario, resumen));
    }
}
