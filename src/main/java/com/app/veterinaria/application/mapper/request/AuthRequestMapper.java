package com.app.veterinaria.application.mapper.request;

import com.app.veterinaria.domain.model.Usuario;
import com.app.veterinaria.domain.valueobject.AuthCredentials;
import com.app.veterinaria.domain.valueobject.VetCreateData;
import com.app.veterinaria.infrastructure.web.dto.request.LoginRequest;
import com.app.veterinaria.infrastructure.web.dto.request.RegisterRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AuthRequestMapper {

    /**
     * Convierte LoginRequest a AuthCredentials
     */
    public AuthCredentials toAuthCredentials(LoginRequest request) {
        return new AuthCredentials(
                request.correo(),
                request.password()
        );
    }

    public Usuario toDomain(RegisterRequest request, PasswordEncoder encoder){
        var data = new VetCreateData(
                request.nombre(),
                request.correo(),
                encoder.encode(request.password()),
                request.telefono(),
                request.dni(),
                request.direccion()
        );
        return Usuario.nuevoVet(data);
    }
}