package com.app.veterinaria.application.service.dueno;

import com.app.veterinaria.application.mapper.DuenoDtoMapper;
import com.app.veterinaria.domain.model.Dueno;
import com.app.veterinaria.domain.repository.DuenoRepository;
import com.app.veterinaria.domain.repository.MascotaRepository;
import com.app.veterinaria.infrastructure.web.dto.details.DuenoDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ListDuenoService {

    private final DuenoRepository duenoRepository;
    private final MascotaRepository mascotaRepository;
    private final DuenoDtoMapper duenoDtoMapper;

    final int LIMIT_SIZE = 10;

    public Flux<DuenoDetails> execute(){
        return duenoRepository.findAll(LIMIT_SIZE)
                .collectList()
                .flatMapMany(duenos -> {
                    if (duenos.isEmpty()) {
                        return Flux.empty();
                    }

                    List<UUID> duenoIds = duenos.stream()
                            .map(Dueno::getId)
                            .toList();

                    return mascotaRepository.findByDuenoIdIn(duenoIds)
                            .collectMultimap(mascota -> mascota.getDueno().getId())
                            .flatMapMany(mascotasByDueno ->
                                    Flux.fromIterable(duenos)
                                            .map(dueno -> {
                                                var mascotas = mascotasByDueno.get(dueno.getId());
                                                dueno.setMascotas(mascotas != null ? new ArrayList<>(mascotas) : new ArrayList<>());
                                                return duenoDtoMapper.toDetails(dueno);
                                            })
                            );
                });
    }
}
