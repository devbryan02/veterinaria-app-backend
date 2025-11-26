package com.app.veterinaria.application.service.auth;

import com.app.veterinaria.application.repository.AuthQueryRepository;
import com.app.veterinaria.shared.exception.auth.InvalidCredentialsException;
import com.app.veterinaria.shared.security.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {

    private final AuthQueryRepository authQueryRepository;

    @Override
    public Mono<UserDetails> findByUsername(String correo) {
        log.debug("Cargando usuario con correo: {}", correo);

        return authQueryRepository.findByCorreoWithRoles(correo)
                .map(CustomUserDetails::from)
                .cast(UserDetails.class)
                .doOnSuccess(userDetails ->
                        log.debug("Usuario cargado exitosamente: {}", correo)
                )
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Usuario no encontrado con correo: {}", correo);
                    return Mono.error(new InvalidCredentialsException(
                            "No se encontró un usuario con el correo: " + correo
                    ));
                }));
    }
}