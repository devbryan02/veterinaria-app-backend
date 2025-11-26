package com.app.veterinaria.application.service.auth;

import com.app.veterinaria.application.repository.AuthQueryRepository;
import com.app.veterinaria.application.service.veterinaria.VetCreateService;
import com.app.veterinaria.domain.model.Rol;
import com.app.veterinaria.domain.model.Usuario;
import com.app.veterinaria.domain.valueobject.AuthCredentials;
import com.app.veterinaria.domain.valueobject.AuthToken;
import com.app.veterinaria.infrastructure.web.dto.request.LoginRequest;
import com.app.veterinaria.infrastructure.web.dto.request.RegisterRequest;
import com.app.veterinaria.infrastructure.web.dto.response.AuthResponse;
import com.app.veterinaria.infrastructure.web.dto.response.UserInfoResponse;
import com.app.veterinaria.shared.exception.auth.InvalidCredentialsException;
import com.app.veterinaria.application.mapper.request.AuthRequestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final AuthQueryRepository authQueryRepository;
    private final VetCreateService vetCreateService;
    private final TokenService tokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthRequestMapper authRequestMapper;

    public Mono<AuthResponse> login(LoginRequest loginRequest) {
        log.info("Iniciando proceso de login para: {}", loginRequest.correo());

        AuthCredentials credentials = authRequestMapper.toAuthCredentials(loginRequest);

        return authQueryRepository.findByCorreoWithRoles(credentials.correo())
                .switchIfEmpty(Mono.error(new InvalidCredentialsException(
                        "No existe un usuario con el correo: " + credentials.correo()
                )))
                .flatMap(usuario -> validateUserAndGenerateToken(usuario, credentials.password()))
                .doOnSuccess(response ->
                        log.info("Login exitoso para usuario: {}", loginRequest.correo())
                )
                .doOnError(error ->
                        log.error("Error en login para {}: {}", loginRequest.correo(), error.getMessage())
                );
    }

    public Mono<AuthResponse> register(RegisterRequest registerRequest) {
        log.info("Iniciando proceso de registro para: {}", registerRequest.correo());
        return vetCreateService.crearUsuarioConRol(registerRequest)
                .flatMap(this::generateAuthResponse);
    }

    public Mono<AuthResponse> refreshToken(String authHeader) {
        log.info("Intentando refrescar token");

        String refreshToken = extractTokenFromHeader(authHeader);

        return tokenService.validateToken(refreshToken)
                .flatMap(isValid -> {
                    if (!isValid) {
                        return Mono.error(new InvalidCredentialsException(
                                "Refresh token inválido o expirado"
                        ));
                    }
                    return tokenService.extractUsername(refreshToken);
                })
                .flatMap(authQueryRepository::findByCorreoWithRoles)
                .flatMap(this::generateAuthResponse)
                .doOnSuccess(response -> log.info("Token refrescado exitosamente"))
                .doOnError(error -> log.error("Error refrescando token: {}", error.getMessage()));
    }

    public Mono<UserInfoResponse> getCurrentUserInfo(String authHeader) {
        log.info("Obteniendo información del usuario actual");

        String token = extractTokenFromHeader(authHeader);

        return getCurrentUser(token)
                .map(this::buildUserInfoResponse)
                .doOnSuccess(response -> log.info("Información de usuario obtenida exitosamente"))
                .doOnError(error -> log.error("Error obteniendo usuario: {}", error.getMessage()));
    }

    private Mono<Usuario> getCurrentUser(String token) {
        return tokenService.extractUsername(token)
                .flatMap(authQueryRepository::findByCorreoWithRoles)
                .switchIfEmpty(Mono.error(new InvalidCredentialsException(
                        "Usuario no encontrado"
                )));
    }

    private Mono<AuthResponse> validateUserAndGenerateToken(Usuario usuario, String rawPassword) {
        if (!usuario.activo()) {
            return Mono.error(new InvalidCredentialsException(
                    "La cuenta está desactivada. Contacta al administrador"
            ));
        }

        if (!usuario.cuentaNoBloqueada()) {
            return Mono.error(new InvalidCredentialsException(
                    "La cuenta está bloqueada. Contacta al administrador"
            ));
        }

        boolean passwordMatches = passwordEncoder.matches(rawPassword, usuario.passwordHash());

        if (!passwordMatches) {
            log.warn("Intento de login fallido para: {}", usuario.correo());
            return Mono.error(new InvalidCredentialsException(
                    "Contraseña incorrecta"
            ));
        }

        return generateAuthResponse(usuario);
    }

    private Mono<AuthResponse> generateAuthResponse(Usuario usuario) {
        return tokenService.generateAuthToken(usuario)
                .map(authToken -> buildAuthResponse(authToken, usuario));
    }

    private AuthResponse buildAuthResponse(AuthToken authToken, Usuario usuario) {
        List<String> roleNames = usuario.roles().stream()
                .map(Rol::nombre)
                .collect(Collectors.toList());

        return AuthResponse.of(
                authToken.token(),
                authToken.refreshToken(),
                authToken.expiresAt(),
                usuario.id().toString(),
                usuario.nombre(),
                usuario.correo(),
                roleNames
        );
    }

    private String extractTokenFromHeader(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidCredentialsException("Header de autorización inválido");
        }
        return authHeader.substring(7);
    }

    private UserInfoResponse buildUserInfoResponse(Usuario usuario) {
        List<String> roleNames = usuario.roles().stream()
                .map(Rol::nombre)
                .collect(Collectors.toList());

        return new UserInfoResponse(
                usuario.id().toString(),
                usuario.nombre(),
                usuario.correo(),
                roleNames
        );
    }
}