CREATE TABLE vacuna
(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    tipo VARCHAR(255) NOT NULL,
    fecha_aplicacion DATE,
    mascota_id UUID NOT NULL,
    CONSTRAINT fk_mascota_vacuna FOREIGN KEY (mascota_id) REFERENCES mascota(id)
);
