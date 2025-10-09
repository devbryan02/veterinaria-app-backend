package com.app.veterinaria.shared.exception;

import com.app.veterinaria.infrastructure.web.dto.response.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalHandlerException {

    // Exceptiones del negocio
    @ExceptionHandler(BusinessException.class)
    public Mono<ResponseEntity<ApiErrorResponse>> handleBusinessException(
            BusinessException ex,
            ServerWebExchange exchange) {

        log.error("Business error [{}]: {}", ex.getClass().getSimpleName(), ex.getMessage());

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(ex.getStatus().value())
                .error(ex.getClass().getSimpleName().replace("Exception", ""))
                .message(ex.getMessage())
                .path(exchange.getRequest().getURI().getPath())
                .build();

        return Mono.just(ResponseEntity.status(ex.getStatus()).body(apiErrorResponse));
    }

    // Validaciones de @Valid en WebFlux
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<ResponseEntity<ApiErrorResponse>> handleValidationException(
            WebExchangeBindException ex,
            ServerWebExchange exchange) {

        log.error("Validation error: {}", ex.getMessage());

        // Extrae todos los errores de validación
        Map<String, Object> validationErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                validationErrors.put(error.getField(), error.getDefaultMessage())
        );

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("ValidationError")
                .message("Error en la validación de los datos")
                .path(exchange.getRequest().getURI().getPath())
                .details(validationErrors)
                .build();

        return Mono.just(ResponseEntity.badRequest().body(apiErrorResponse));
    }

    // Errores de deserialización JSON
    @ExceptionHandler(ServerWebInputException.class)
    public Mono<ResponseEntity<ApiErrorResponse>> handleServerWebInputException(
            ServerWebInputException ex,
            ServerWebExchange exchange) {

        log.error("Input error: {}", ex.getMessage());

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("InvalidInput")
                .message("Datos de entrada inválidos: " + ex.getReason())
                .path(exchange.getRequest().getURI().getPath())
                .build();

        return Mono.just(ResponseEntity.badRequest().body(apiErrorResponse));
    }

    // Catch-all para cualquier excepción no manejada
    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ApiErrorResponse>> handleGlobalException(
            Exception ex,
            ServerWebExchange exchange) {

        log.error("Unexpected error: ", ex);

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("InternalServerError")
                .message("Ha ocurrido un error inesperado. Por favor, contacta al administrador.")
                .path(exchange.getRequest().getURI().getPath())
                .build();

        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorResponse));
    }
}