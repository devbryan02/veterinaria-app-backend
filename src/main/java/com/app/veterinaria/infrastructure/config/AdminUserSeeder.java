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
import java.util.Random;
import java.util.UUID;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AdminUserSeeder {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final PasswordEncoder passwordEncoder;
    private final Random random = new Random();

    @Value("${admin.email:}")
    private String adminEmail;

    @Value("${admin.password:}")
    private String adminPassword;

    @Bean
    public CommandLineRunner seedAdmins() {
        return args -> {
            // Validar que existan credenciales
            if (adminEmail == null || adminEmail.isBlank() ||
                    adminPassword == null || adminPassword.isBlank()) {
                log.info("No hay credenciales de admin configuradas en variables de entorno.");
                return;
            }

            crearAdminSiNoExiste()
                    .doOnError(e -> log.error("Error creando usuario ADMIN", e))
                    .block();
        };
    }

    private Mono<Void> crearAdminSiNoExiste() {
        return usuarioRepository.findByCorreo(adminEmail)
                .flatMap(existing -> {
                    log.info("Usuario admin {} ya existe. Se omite creación.", adminEmail);
                    return Mono.empty();
                })
                .switchIfEmpty(crearAdmin())
                .then();
    }

    private Mono<Void> crearAdmin() {

        Usuario usuario = new Usuario(
                null,
                "Administrador",
                adminEmail,
                passwordEncoder.encode(adminPassword),
                generarTelefono(),
                generarDni(),
                "Central Admin",
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

        return usuarioRepository.save(usuario)
                .flatMap(saved -> asignarRolAdmin(saved.id()))
                .doOnSuccess(v -> log.info("Usuario ADMIN creado exitosamente: {}", adminEmail))
                .onErrorResume(org.springframework.dao.DuplicateKeyException.class, e -> {
                    log.warn("El usuario {} ya existe en BD (clave duplicada). Se omite.", adminEmail);
                    return Mono.empty();
                })
                .then();
    }

    private Mono<Void> asignarRolAdmin(UUID usuarioId) {
        return rolRepository.findByNombre("ADMIN")
                .flatMap(rol -> {
                    UsuarioRol usuarioRol = new UsuarioRol(
                            null,
                            usuarioId,
                            rol.id(),
                            LocalDateTime.now()
                    );
                    return usuarioRolRepository.save(usuarioRol);
                })
                .doOnSuccess(v -> log.info("Rol ADMIN asignado correctamente"))
                .then();
    }

    /**
     * Genera un teléfono móvil
     */
    private String generarTelefono() {
        int numero = 10_000_000 + random.nextInt(90_000_000);
        return "9" + numero;
    }

    /**
     * Genera un DNI peruano válido
     */
    private String generarDni() {
        return String.valueOf(10_000_000 + random.nextInt(90_000_000));
    }
}