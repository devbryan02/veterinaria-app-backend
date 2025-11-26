package com.app.veterinaria.domain.model;

import com.app.veterinaria.domain.emuns.EstadoMascota;
import com.app.veterinaria.domain.emuns.SexoEnum;
import com.app.veterinaria.domain.valueobject.MascotaDataCreate;
import com.app.veterinaria.domain.valueobject.MascotaDataUpdate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record Mascota(
        UUID id,
        UUID usuarioId,
        String identificador,
        String nombre,
        String especie,
        String raza,
        SexoEnum sexo,
        String temperamento,
        String condicionReproductiva,
        String color,
        Integer anios,
        Integer meses,
        EstadoMascota estado,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Usuario usuario,
        List<Imagen> imagenes
) {

    public static Mascota nuevo(MascotaDataCreate data){
        return new Mascota(
                null,
                data.usuarioId(),
                null,
                data.nombre(),
                data.especie(),
                data.raza(),
                data.sexo(),
                data.temperamento(),
                data.condicionReproductiva(),
                data.color(),
                data.anios(),
                data.meses(),
                EstadoMascota.ACTIVO,
                LocalDateTime.now(),
                null,
                null,
                List.of()
        );
    }

    public Mascota actualizar(MascotaDataUpdate data){
        return new Mascota(
                this.id,
                this.usuarioId,
                this.identificador,
                data.nombre(),
                data.especie(),
                data.raza(),
                data.sexo(),
                data.temperamento(),
                data.condicionReproductiva(),
                data.color(),
                data.anios(),
                data.meses(),
                data.estado(),
                this.createdAt,
                LocalDateTime.now(),
                this.usuario,
                this.imagenes
        );
    }
}