CREATE TABLE dueno
(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    nombre VARCHAR(255) NOT NULL,
    dni VARCHAR(20) UNIQUE,
    direccion VARCHAR(255),
    telefono VARCHAR(20),
    correo VARCHAR(255) UNIQUE,
    password VARCHAR(255) NOT NULL,
    latitud VARCHAR(255),
    longitud VARCHAR(255)
);
