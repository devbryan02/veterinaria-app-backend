CREATE TABLE mascota
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(255) NOT NULL,
    especie VARCHAR(255) NOT NULL,
    raza VARCHAR(255),
    edad VARCHAR(50),
    sexo CHAR(1),
    temperatura VARCHAR(50),
    condicion VARCHAR(255),
    color VARCHAR(255),
    dueno_id UUID NOT NULL,
    CONSTRAINT fk_dueno_mascota FOREIGN KEY (dueno_id) REFERENCES dueno(id)
);
