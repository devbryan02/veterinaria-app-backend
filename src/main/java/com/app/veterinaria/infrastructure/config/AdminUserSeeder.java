package com.app.veterinaria.infrastructure.config;

import com.app.veterinaria.domain.model.Usuario;
import com.app.veterinaria.domain.model.UsuarioRol;
import com.app.veterinaria.domain.repository.RolRepository;
import com.app.veterinaria.domain.repository.UsuarioRepository;
import com.app.veterinaria.domain.repository.UsuarioRolRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AdminUserSeeder {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email:admin@veterinaria.com}")
    private String adminEmail;

    @Value("${admin.password:admin123}")
    private String adminPassword;

    @Bean
    public CommandLineRunner seedAdminUser() {
        return args -> {
            log.info("Verificando usuario administrador...");

            usuarioRepository.findByCorreo(adminEmail)
                    .flatMap(existingUser -> {
                        log.info("Usuario administrador ya existe: {}", adminEmail);
                        return Mono.empty();
                    })
                    .switchIfEmpty(
                            Mono.defer(() -> {
                                log.info("Creando usuario administrador...");
                                return createAdminUser();
                            })
                    )
                    .doOnError(error -> log.error("❌ Error: {}", error.getMessage()))
                    .onErrorResume(e -> Mono.empty())
                    .block();
        };
    }

    private Mono<Void> createAdminUser() {
        Usuario admin = new Usuario(
                null,
                "Administrador del Sistema",
                adminEmail,
                passwordEncoder.encode(adminPassword),
                "999999999",
                "00000000",
                "Oficina Central",
                null,
                null,
                true,
                true,
                true,
                true,
                LocalDateTime.now(),
                LocalDateTime.now(),
                List.of()
        );

        return usuarioRepository.save(admin)
                .doOnSuccess(user -> log.info("Usuario creado: {}", user.correo()))
                .flatMap(user -> asignarRolAdmin(user.id()))
                .then();
    }

    private Mono<Void> asignarRolAdmin(UUID usuarioId) {
        return rolRepository.findByNombre("ADMIN")
                .flatMap(rol -> {
                    UsuarioRol usuarioRol = new UsuarioRol(null, usuarioId, rol.id(), LocalDateTime.now());
                    return usuarioRolRepository.save(usuarioRol);
                })
                .doOnSuccess(ur -> log.info("Rol ADMIN asignado"))
                .then();
    }
}