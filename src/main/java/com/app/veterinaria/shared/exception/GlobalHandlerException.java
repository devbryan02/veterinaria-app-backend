package com.app.veterinaria.shared.exception;

import com.app.veterinaria.infrastructure.web.dto.response.ApiErrorResponse;
import io.r2dbc.spi.R2dbcDataIntegrityViolationException;
import io.r2dbc.spi.R2dbcException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

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

        String errorMessage = ex.getAllErrors().stream()
                .findFirst()
                .map(MessageSourceResolvable::getDefaultMessage)
                .orElse("Error en la validación de los datos");

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("ValidationError")
                .message(errorMessage)
                .path(exchange.getRequest().getURI().getPath())
                .details(null)
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

    //handler para errores de R2DBC
    @ExceptionHandler(R2dbcDataIntegrityViolationException.class)
    public Mono<ResponseEntity<ApiErrorResponse>> handleDataIntegrityViolation(
            R2dbcDataIntegrityViolationException ex,
            ServerWebExchange exchange) {

        log.error("Error de integridad de datos: {}", ex.getMessage());

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error("DataIntegrityViolation")
                .message("Ya existe una mascota con esos datos")
                .path(exchange.getRequest().getURI().getPath())
                .build();

        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(apiErrorResponse));
    }

    @ExceptionHandler(R2dbcException.class)
    public Mono<ResponseEntity<ApiErrorResponse>> handleR2dbcException(
            R2dbcException ex,
            ServerWebExchange exchange) {

        log.error("Error de base de datos: {}", ex.getMessage());

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("DatabaseError")
                .message("Error al procesar la operación en la base de datos")
                .path(exchange.getRequest().getURI().getPath())
                .build();

        return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiErrorResponse));
    }

    //Captura errores de contrainst nullos, unique, fk
    @ExceptionHandler(DataIntegrityViolationException.class)
    public Mono<ResponseEntity<ApiErrorResponse>> handleSpringDataIntegrityViolation(
            DataIntegrityViolationException ex,
            ServerWebExchange exchange) {

        log.error("Error de integridad de datos (Spring DAO): {}", ex.getMessage());

        String message = "Violación de integridad de datos.";
        // Puedes hacer un chequeo del mensaje para personalizar la respuesta:
        if (ex.getMessage() != null && ex.getMessage().contains("violates not-null constraint")) {
            message = "Uno o más campos requeridos no pueden ser nulos.";
        } else if (ex.getMessage() != null && ex.getMessage().contains("duplicate key")) {
            message = "Registro duplicado: ya existe un recurso con esos datos.";
        }

        ApiErrorResponse apiErrorResponse = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error("DataIntegrityViolation")
                .message(message)
                .path(exchange.getRequest().getURI().getPath())
                .build();

        return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(apiErrorResponse));
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