package com.app.veterinaria.infrastructure.web.dto.response;

public record OperationResponseStatus(
        boolean success,
        String message
) {

    // Respuesta de operacion exitosa
    public static OperationResponseStatus ok(String message) {
        return new OperationResponseStatus(true, message);
    }

    // Respuesta de operacion fallida
    public static OperationResponseStatus fail(String message) {
        return new OperationResponseStatus(false, message);
    }
}
