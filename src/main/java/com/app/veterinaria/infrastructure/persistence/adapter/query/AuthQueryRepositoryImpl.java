package com.app.veterinaria.infrastructure.persistence.adapter.query;

import com.app.veterinaria.application.repository.AuthQueryRepository;
import com.app.veterinaria.domain.model.Rol;
import com.app.veterinaria.domain.model.Usuario;
import com.app.veterinaria.infrastructure.mapper.RolMapper;
import com.app.veterinaria.infrastructure.mapper.UsuarioMapper;
import com.app.veterinaria.infrastructure.persistence.adapter.r2dbc.RolR2dbcRepository;
import com.app.veterinaria.infrastructure.persistence.adapter.r2dbc.UsuarioR2dbcRepository;
import com.app.veterinaria.infrastructure.persistence.adapter.r2dbc.UsuarioRolR2dbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class AuthQueryRepositoryImpl implements AuthQueryRepository {

    private final UsuarioR2dbcRepository usuarioR2dbcRepository;
    private final UsuarioRolR2dbcRepository usuarioRolR2dbcRepository;
    private final RolR2dbcRepository rolR2dbcRepository;
    private final UsuarioMapper usuarioMapper;
    private final RolMapper rolMapper;

    @Override
    public Mono<Usuario> findByCorreoWithRoles(String correo) {
        return usuarioR2dbcRepository.findByCorreo(correo)
                .flatMap(usuarioEntity -> {
                    // Buscar los roles del usuario
                    Flux<Rol> rolesFlux = usuarioRolR2dbcRepository
                            .findByUsuarioId(usuarioEntity.getId())
                            .flatMap(usuarioRol ->
                                    rolR2dbcRepository.findById(usuarioRol.getRolId())
                            )
                            .map(rolMapper::toDomain);

                    // Convertir el usuario a dominio y agregar los roles
                    return rolesFlux.collectList()
                            .map(roles -> {
                                Usuario usuario = usuarioMapper.toDomain(usuarioEntity);
                                return new Usuario(
                                        usuario.id(),
                                        usuario.nombre(),
                                        usuario.correo(),
                                        usuario.passwordHash(),
                                        usuario.telefono(),
                                        usuario.dni(),
                                        usuario.direccion(),
                                        usuario.latitud(),
                                        usuario.longitud(),
                                        usuario.activo(),
                                        usuario.cuentaNoExpirada(),
                                        usuario.cuentaNoBloqueada(),
                                        usuario.credencialesNoExpiradas(),
                                        usuario.createdAt(),
                                        usuario.updatedAt(),
                                        roles
                                );
                            });
                });
    }
}