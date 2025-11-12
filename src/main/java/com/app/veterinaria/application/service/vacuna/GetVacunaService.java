package com.app.veterinaria.application.service.vacuna;

import com.app.veterinaria.application.repository.VacunaQueryRepository;
import com.app.veterinaria.infrastructure.web.dto.details.VacunaWithMascotaDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class GetVacunaService {

    private final VacunaQueryRepository vacunaQueryRepository;

    public Mono<VacunaWithMascotaDetails> findById(UUID idVacuna) {
        return vacunaQueryRepository.findById(idVacuna)
                .doOnSubscribe(sub -> log.info("Buscando vacuna con id: " + idVacuna))
                .doOnNext(v -> log.info("Vacuna encontrada: {}", v.tipo()));
    }

    public Flux<VacunaWithMascotaDetails> findAll(){
        return vacunaQueryRepository.findAllVacunaWithMascota()
                .doOnSubscribe(sub -> log.info("Listando todas las vacunas"))
                .doOnComplete(() -> log.info("Listado completado"));
    }

    public Flux<VacunaWithMascotaDetails> findVacunasByDate(LocalDate startDate, LocalDate finalDate) {
        return vacunaQueryRepository.findVacunasByDate(startDate, finalDate)
                .doOnSubscribe(sub -> log.info("Filtrando vacunas de: {} hasta: {}", startDate, finalDate))
                .doOnComplete(() -> log.info("Filtrado completado para {} - {}", startDate, finalDate));
    }

    public Flux<VacunaWithMascotaDetails> filterByTipo(String tipo) {
        return vacunaQueryRepository.filteByTipo(tipo)
                .doOnSubscribe(sub -> log.info("Filtrando vacunas de: {}", tipo))
                .doOnComplete(() -> log.info("Filtrado completado de tipo {} ", tipo));
    }

}
