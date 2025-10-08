CREATE TABLE imagen
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    url VARCHAR(255) NOT NULL,
    fecha_subida DATE,
    descripcion VARCHAR(255),
    mascota_id UUID NOT NULL,
    CONSTRAINT fk_mascota_imagen FOREIGN KEY (mascota_id) REFERENCES mascota(id)
);
