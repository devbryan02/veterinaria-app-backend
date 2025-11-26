package com.app.veterinaria.shared.security.jwt;

import com.app.veterinaria.shared.exception.auth.TokenExpiredException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ott.InvalidOneTimeTokenException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    /**
     * Genera la clave secreta a partir del secret configurado
     */
    private SecretKey getSigningKey() {
        byte[] keyBytes = jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Genera un token JWT para un usuario autenticado
     */
    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return generateToken(userDetails.getUsername(), extractRoles(authentication));
    }

    /**
     * Genera un token JWT con username y roles
     */
    public String generateToken(String username, List<String> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roles);

        return createToken(claims, username, jwtProperties.getExpiration());
    }

    /**
     * Genera un refresh token
     */
    public String generateRefreshToken(String username) {
        return createToken(new HashMap<>(), username, jwtProperties.getRefreshExpiration());
    }

    /**
     * Crea el token JWT con los claims, subject y tiempo de expiración
     */
    private String createToken(Map<String, Object> claims, String subject, Long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrae el username (correo) del token
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
     * Extrae los roles del token
     */
    @SuppressWarnings("unchecked")
    public List<String> getRolesFromToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return (List<String>) claims.get("roles");
        } catch (Exception e) {
            log.error("Error al extraer roles del token: {}", e.getMessage());
            return List.of();
        }
    }

    /**
     * Extrae la fecha de expiración del token
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
     * Valida si el token es válido
     */
    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (SignatureException e) {
            log.error("Firma del token inválida: {}", e.getMessage());
            throw new InvalidOneTimeTokenException("La firma del token no es válida");
        } catch (MalformedJwtException e) {
            log.error("Token JWT malformado: {}", e.getMessage());
            throw new InvalidOneTimeTokenException("El formato del token no es válido");
        } catch (ExpiredJwtException e) {
            log.error("Token JWT expirado: {}", e.getMessage());
            throw new TokenExpiredException("El token ha expirado");
        } catch (UnsupportedJwtException e) {
            log.error("Token JWT no soportado: {}", e.getMessage());
            throw new InvalidOneTimeTokenException("El tipo de token no es soportado");
        } catch (IllegalArgumentException e) {
            log.error("Token JWT vacío o nulo: {}", e.getMessage());
            throw new InvalidOneTimeTokenException("El token está vacío");
        }
    }

    /**
     * Valida si el token es válido para un usuario específico
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        try {
            final String username = getUsernameFromToken(token);
            return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
        } catch (Exception e) {
            log.error("Error validando token para usuario: {}", e.getMessage());
            return false;
        }
    }

    /**
     * Verifica si el token ha expirado
     */
    private boolean isTokenExpired(String token) {
        try {
            Claims claims = extractAllClaims(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }

    /**
     * Extrae todos los claims del token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Extrae los roles de la autenticación
     */
    private List<String> extractRoles(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    /**
     * Calcula el tiempo restante hasta la expiración en milisegundos
     */
    public Long getExpirationTimeInMillis(String token) {
        try {
            Claims claims = extractAllClaims(token);
            Date expiration = claims.getExpiration();
            return expiration.getTime() - System.currentTimeMillis();
        } catch (Exception e) {
            log.error("Error calculando tiempo de expiración: {}", e.getMessage());
            return 0L;
        }
    }
}