package com.app.veterinaria.infrastructure.web.dto.response;

import java.util.List;

public record UserInfoResponse(
        String id,
        String nombre,
        String correo,
        List<String> roles
) {}