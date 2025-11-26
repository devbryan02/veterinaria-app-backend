package com.app.veterinaria.application.service.mascota;

import com.app.veterinaria.application.mapper.response.MascotaResponseMapper;
import com.app.veterinaria.domain.model.Mascota;
import com.app.veterinaria.domain.repository.ImagenRepository;
import com.app.veterinaria.domain.repository.MascotaRepository;
import com.app.veterinaria.domain.repository.UsuarioRepository;
import com.app.veterinaria.domain.repository.VacunaRepository;
import com.app.veterinaria.infrastructure.web.dto.details.*;
import com.app.veterinaria.infrastructure.web.dto.details.resume.DuenoResumen;
import com.app.veterinaria.infrastructure.web.dto.details.resume.ImagenResumen;
import com.app.veterinaria.infrastructure.web.dto.details.resume.VacunasResumen;
import com.app.veterinaria.shared.exception.mascota.MascotaNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetMascotaDetailsService {

    private final MascotaRepository mascotaRepository;
    private final UsuarioRepository usuarioRepository;
    private final VacunaRepository vacunaRepository;
    private final ImagenRepository imagenRepository;
    private final MascotaResponseMapper mapper;

    public Mono<MascotaFullDetails> execute(UUID mascotaId) {

        return mascotaRepository.findById(mascotaId)
                .switchIfEmpty(Mono.error(new MascotaNotFoundException("Mascota no encontrada")))
                .flatMap(this::loadRelations);
    }

    private Mono<MascotaFullDetails> loadRelations(Mascota mascota) {

        Mono<DuenoResumen> duenoMono = usuarioRepository.findById(mascota.usuario().id())
                        .map(mapper::toDuenoResumen);

        Mono<VacunasResumen> vacunasMono = vacunaRepository.findByMascotaId(mascota.id())
                        .map(mapper::toVacunaDetalle)
                        .collectList()
                        .map(vacunas -> new VacunasResumen(
                                vacunas.size(),
                                vacunas
                        ));

        Mono<List<ImagenResumen>> imagenesMono = imagenRepository.findByMascotaId(mascota.id())
                        .map(mapper::toImagenResumen)
                        .collectList();

        return Mono.zip(duenoMono, vacunasMono, imagenesMono)
                .map(tuple -> mapper.toDetails(
                        mascota,
                        tuple.getT1(), // dueno
                        tuple.getT2(), // vacunas
                        tuple.getT3(), // imagenes
                        getEdadString(mascota),
                        getFotoPrincipal(tuple.getT3())
                ));
    }

    private String getEdadString(Mascota mascota) {
        if (mascota.anios() == null || mascota.meses() == null) return null;
        return mascota.anios() + " años, " + mascota.meses() + " meses";
    }

    private String getFotoPrincipal(List<ImagenResumen> imagenes) {
        if (imagenes.isEmpty()) return null;
        return imagenes.get(0).url();
    }

}
