package com.app.veterinaria.shared.security.userdetails;

import com.app.veterinaria.domain.model.Rol;
import com.app.veterinaria.domain.model.Usuario;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementación de UserDetails que envuelve nuestro modelo de dominio Usuario
 * Adapta el Usuario a lo que Spring Security necesita
 */
@Getter
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final Usuario usuario;

    /**
     * Retorna los roles del usuario como GrantedAuthority
     * Spring Security usa esto para las autorizaciones
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (usuario.roles() == null || usuario.roles().isEmpty()) {
            return List.of();
        }

        return usuario.roles().stream()
                .map(Rol::nombre)
                .map(rolNombre -> new SimpleGrantedAuthority("ROLE_" + rolNombre))
                .collect(Collectors.toList());
    }

    /**
     * Retorna la contraseña hasheada del usuario
     */
    @Override
    public String getPassword() {
        return usuario.passwordHash();
    }

    /**
     * Retorna el correo como username (identificador único)
     */
    @Override
    public String getUsername() {
        return usuario.correo();
    }

    /**
     * Verifica si la cuenta no ha expirado
     */
    @Override
    public boolean isAccountNonExpired() {
        return usuario.cuentaNoExpirada();
    }

    /**
     * Verifica si la cuenta no está bloqueada
     */
    @Override
    public boolean isAccountNonLocked() {
        return usuario.cuentaNoBloqueada();
    }

    /**
     * Verifica si las credenciales no han expirado
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return usuario.credencialesNoExpiradas();
    }

    /**
     * Verifica si la cuenta está activa
     */
    @Override
    public boolean isEnabled() {
        return usuario.activo();
    }

    /**
     * Métodos de utilidad adicionales
     */

    public UUID getId() {
        return usuario.id();
    }

    public String getNombre() {
        return usuario.nombre();
    }

    public String getCorreo() {
        return usuario.correo();
    }

    public List<String> getRoleNames() {
        return usuario.roles().stream()
                .map(Rol::nombre)
                .collect(Collectors.toList());
    }

    /**
     * Factory method para crear desde Usuario
     */
    public static CustomUserDetails from(Usuario usuario) {
        return new CustomUserDetails(usuario);
    }
}