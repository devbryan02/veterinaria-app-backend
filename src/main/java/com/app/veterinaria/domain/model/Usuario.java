package com.app.veterinaria.domain.model;

import com.app.veterinaria.domain.valueobject.DuenoDataCreate;
import com.app.veterinaria.domain.valueobject.DuenoDataUpdate;
import com.app.veterinaria.domain.valueobject.VetCreateData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record Usuario(
        UUID id,
        String nombre,
        String correo,
        String passwordHash,
        String telefono,
        String dni,
        String direccion,
        String latitud,
        String longitud,
        Boolean activo,
        Boolean cuentaNoExpirada,
        Boolean cuentaNoBloqueada,
        Boolean credencialesNoExpiradas,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        List<Rol> roles
) {

    public static Usuario nuevoDueno(DuenoDataCreate data){
        return new Usuario(
                null,
                data.nombre(),
                data.correo(),
                data.passwordHash(),
                data.telefono(),
                data.dni(),
                data.direccion(),
                data.latitud(),
                data.longitud(),
                true,
                true,
                true,
                true,
                LocalDateTime.now(),
                null,
                List.of()
        );
    }

    public static Usuario nuevoVet(VetCreateData data){
        return new Usuario(
                null,
                data.nombre(),
                data.correo(),
                data.passwordHash(),
                data.telefono(),
                data.dni(),
                data.direccion(),
                null,
                null,
                true,
                true,
                true,
                true,
                LocalDateTime.now(),
                null,
                List.of()
        );
    }

    public Usuario actualizar(DuenoDataUpdate data){
        return new Usuario(
                this.id,
                data.nombre(),
                data.correo(),
                this.passwordHash,
                data.telefono(),
                data.dni(),
                data.direccion(),
                this.latitud,
                this.longitud,
                this.activo,
                this.cuentaNoExpirada,
                this.cuentaNoBloqueada,
                this.credencialesNoExpiradas,
                this.createdAt,
                LocalDateTime.now(),
                this.roles
        );
    }

    public Usuario conCuentaBloqueada(Boolean cuentaNoBloqueada){
        return new Usuario(
                this.id,
                this.nombre(),
                this.correo(),
                this.passwordHash,
                this.telefono(),
                this.dni(),
                this.direccion(),
                this.latitud,
                this.longitud,
                this.activo,
                this.cuentaNoExpirada,
                cuentaNoBloqueada,
                this.credencialesNoExpiradas,
                this.createdAt,
                LocalDateTime.now(),
                this.roles
        );
    }
}
