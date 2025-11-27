package com.app.veterinaria. shared. security. jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken. security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication. ott.InvalidOneTimeTokenException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time. Instant;
import java.time.LocalDateTime;
import java. time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtProperties. getSecret().getBytes(StandardCharsets. UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Genera access token con username y roles
     */
    public String generateToken(String username, List<String> roles) {
        try {
            Map<String, Object> claims = new HashMap<>();
            List<String> normalizedRoles = roles == null ? Collections.emptyList() : roles;
            claims.put("roles", normalizedRoles);

            log.debug("Generando access token para usuario: {} con roles: {}", username, normalizedRoles);
            return createToken(claims, username, jwtProperties.getExpiration());
        } catch (Exception e) {
            log.error("Error generando token para usuario {}: {}", username, e.getMessage());
            throw new InvalidOneTimeTokenException("No se pudo generar el token");
        }
    }

    /**
     * Genera refresh token
     */
    public String generateRefreshToken(String username) {
        try {
            log.debug("Generando refresh token para usuario: {}", username);
            return createToken(new HashMap<>(), username, jwtProperties.getRefreshExpiration());
        } catch (Exception e) {
            log.error("Error generando refresh token para usuario {}: {}", username, e.getMessage());
            throw new InvalidOneTimeTokenException("No se pudo generar el refresh token");
        }
    }

    private String createToken(Map<String, Object> claims, String subject, Long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                . compact();
    }

    /**
     * Extrae username del token
     */
    public String getUsernameFromToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.getSubject();
        } catch (Exception e) {
            log.error("Error al extraer username del token: {}", e.getMessage());
            throw new InvalidOneTimeTokenException("No se pudo extraer el username del token");
        }
    }

    /**
     * Extrae fecha de expiración del token
     */
    public LocalDateTime getExpirationDateFromToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            Date expiration = claims.getExpiration();
            return Instant.ofEpochMilli(expiration.getTime())
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime();
        } catch (Exception e) {
            log.error("Error al extraer fecha de expiración: {}", e.getMessage());
            return LocalDateTime.now();
        }
    }

    /**
     * Valida el token
     */
    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (SignatureException | MalformedJwtException | ExpiredJwtException |
                 UnsupportedJwtException | IllegalArgumentException e) {
            log.debug("Token inválido: {}", e. getMessage());
            return false;
        } catch (Exception e) {
            log.error("Error inesperado validando token: {}", e.getMessage());
            return false;
        }
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}