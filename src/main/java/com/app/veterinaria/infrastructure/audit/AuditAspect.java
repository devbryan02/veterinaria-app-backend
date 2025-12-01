package com.app.veterinaria.infrastructure.audit;

import com.app.veterinaria.domain.model.Auditoria;
import com.app.veterinaria.domain.repository.AuditoriaRepository;
import com. app.veterinaria.shared. security.userdetails.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j. Slf4j;
import org. aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation. Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http. server.reactive.ServerHttpRequest;
import org.springframework.security. core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org. springframework.security.core.context.SecurityContext;
import org.springframework.stereotype. Component;
import org.springframework. web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java. util.UUID;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditAspect {

    private final AuditoriaRepository auditoriaRepository;

    @Around("@annotation(auditable)")
    public Object audit(ProceedingJoinPoint joinPoint, Auditable auditable) throws Throwable {
        Object[] args = joinPoint. getArgs();
        ServerWebExchange exchange = extraerExchange(args);

        Object result = joinPoint.proceed();

        if (result instanceof Mono<?> mono) {
            return mono.flatMap(value ->
                    obtenerUserId()
                            .flatMap(userId -> guardarAuditoria(auditable, userId, exchange))
                            .thenReturn(value)
                            .onErrorResume(err -> {
                                log. error("❌ Error en auditoría (no afecta operación): {}", err.getMessage());
                                return Mono.just(value);
                            })
            );
        }

        return result;
    }

    private Mono<UUID> obtenerUserId() {
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getPrincipal)
                .mapNotNull(principal -> {
                    if (principal instanceof CustomUserDetails userDetails) {
                        log.debug("UserId extraído: {}", userDetails.getId());
                        return userDetails.getId();
                    }
                    log.debug("Principal no es CustomUserDetails: {}", principal. getClass().getSimpleName());
                    return null;
                })
                .switchIfEmpty(Mono.defer(() -> {
                    log.debug("ℹ️ No hay SecurityContext o userId disponible");
                    return Mono.just((UUID) null);
                }))
                .onErrorResume(err -> {
                    log.warn("Error obteniendo userId: {}", err.getMessage());
                    return Mono.just((UUID) null);
                });
    }

    private Mono<Void> guardarAuditoria(Auditable auditable, UUID userId, ServerWebExchange exchange) {
        String ip = obtenerIp(exchange);

        Auditoria audit = Auditoria.create(
                userId,
                auditable.action(),
                auditable.entity(),
                ip
        );

        log.info("Auditoría: action={}, entity={}, userId={}, ip={}",
                auditable.action(), auditable.entity(),
                userId != null ? userId : "NULL", ip);

        return auditoriaRepository.save(audit)
                .doOnSuccess(saved -> log.info("Auditoría guardada: {}", saved.id()))
                .then();
    }

    private ServerWebExchange extraerExchange(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof ServerWebExchange exchange) {
                return exchange;
            }
        }
        return null;
    }

    private String obtenerIp(ServerWebExchange exchange) {
        if (exchange == null) {
            return "UNKNOWN";
        }

        try {
            ServerHttpRequest request = exchange.getRequest();

            String forwardedFor = request.getHeaders().getFirst("X-Forwarded-For");
            if (forwardedFor != null && !forwardedFor.isEmpty()) {
                return forwardedFor.split(",")[0].trim();
            }

            String realIp = request.getHeaders().getFirst("X-Real-IP");
            if (realIp != null && !realIp.isEmpty()) {
                return realIp;
            }

            if (request.getRemoteAddress() != null) {
                String ip = request.getRemoteAddress().getAddress().getHostAddress();
                return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
            }

            return "UNKNOWN";

        } catch (Exception e) {
            log.error("Error extrayendo IP: {}", e.getMessage());
            return "UNKNOWN";
        }
    }
}