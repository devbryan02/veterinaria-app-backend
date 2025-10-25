package com.app.veterinaria.application.service.mascota;

import com.app.veterinaria.application.mapper.MascotaDtoMapper;
import com.app.veterinaria.domain.repository.DuenoRepository;
import com.app.veterinaria.domain.repository.MascotaRepository;
import com.app.veterinaria.infrastructure.web.dto.details.MascotaWithDuenoDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListMascotasService {

    private final MascotaRepository mascotaRepository;
    private final DuenoRepository duenoRepository;
    private final MascotaDtoMapper mascotaDtoMapper;

    public Flux<MascotaWithDuenoDetails> execute(){
        return mascotaRepository.findAll()
                .flatMap(mascota -> {
                    if(mascota.getDueno() == null || mascota.getDueno().getId() == null){
                        return Flux.just(mascotaDtoMapper.toDetails(mascota));
                    }
                    return duenoRepository.findById(mascota.getDueno().getId())
                            .map(dueno -> {
                                mascota.setDueno(dueno);
                                return mascotaDtoMapper.toDetails(mascota);
                            })
                            .flux();
                });
    }
}
