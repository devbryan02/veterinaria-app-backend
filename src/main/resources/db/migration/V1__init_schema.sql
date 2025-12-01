-- =============================================
-- SCHEMA: Sistema de Gestión de Mascotas
-- PostgreSQL 15+
-- =============================================

-- =============================================
-- TABLA: rol
-- =============================================
CREATE TABLE rol
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(50) NOT NULL UNIQUE,
    descripcion VARCHAR(255)
);

-- Valores iniciales
INSERT INTO rol (nombre, descripcion) VALUES
('ADMIN', 'Administrador del sistema'),
('DUENO', 'Rol por defecto para usuarios'),
('VETERINARIA', 'Gestiona mascotas, dueños, vacunas y reportes');

-- =============================================
-- TABLA: usuario
-- Compatible con Spring Security UserDetails
-- =============================================
CREATE TABLE usuario
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nombre VARCHAR(255) NOT NULL,
    correo VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    telefono VARCHAR(20),
    dni VARCHAR(20) NOT NULL UNIQUE,
    direccion VARCHAR(255),
    latitud VARCHAR(255),
    longitud VARCHAR(255),

    -- Campos para Spring Security UserDetails
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    cuenta_no_expirada BOOLEAN NOT NULL DEFAULT TRUE,
    cuenta_no_bloqueada BOOLEAN NOT NULL DEFAULT TRUE,
    credenciales_no_expiradas BOOLEAN NOT NULL DEFAULT TRUE,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_usuario_correo ON usuario(correo);
CREATE INDEX idx_usuario_dni ON usuario(dni);
CREATE INDEX idx_usuario_activo ON usuario(activo);

-- =============================================
-- TABLA: usuario_rol (pivote)
-- =============================================
CREATE TABLE usuario_rol
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id UUID NOT NULL,
    rol_id UUID NOT NULL,
    asignado_en TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_usuario_rol_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE,
    CONSTRAINT fk_usuario_rol_rol
        FOREIGN KEY (rol_id) REFERENCES rol(id) ON DELETE RESTRICT,
    CONSTRAINT uk_usuario_rol UNIQUE (usuario_id, rol_id)
);

CREATE INDEX idx_usuario_rol_usuario ON usuario_rol(usuario_id);
CREATE INDEX idx_usuario_rol_rol ON usuario_rol(rol_id);

-- =============================================
-- TABLA: mascota
-- =============================================
CREATE TABLE mascota
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id UUID NOT NULL,
    identificador VARCHAR(30) UNIQUE,
    nombre VARCHAR(255) NOT NULL,
    especie VARCHAR(255) NOT NULL,
    raza VARCHAR(255),
    sexo VARCHAR(10) CHECK (sexo IN ('MACHO', 'HEMBRA')),
    temperamento VARCHAR(50),
    condicion_reproductiva VARCHAR(255),
    color VARCHAR(255),
    anios INTEGER DEFAULT 0 CHECK (anios >= 0),
    meses INTEGER DEFAULT 0 CHECK (meses >= 0 AND meses < 12),
    estado VARCHAR(50) CHECK ( estado IN ('ACTIVO', 'FALLECIDO', 'EN_ADOPCION')),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_mascota_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE CASCADE
);

CREATE INDEX idx_mascota_usuario ON mascota(usuario_id);
CREATE INDEX idx_mascota_identificador ON mascota(identificador);
CREATE INDEX idx_mascota_especie ON mascota(especie);

-- =============================================
-- TABLA: imagen
-- =============================================
CREATE TABLE imagen
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    mascota_id UUID NOT NULL,
    url VARCHAR(500) NOT NULL,
    descripcion TEXT,
    fecha_subida DATE NOT NULL DEFAULT CURRENT_DATE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_imagen_mascota
        FOREIGN KEY (mascota_id) REFERENCES mascota(id) ON DELETE CASCADE
);

CREATE INDEX idx_imagen_mascota ON imagen(mascota_id);
CREATE INDEX idx_imagen_fecha_subida ON imagen(fecha_subida DESC);

-- =============================================
-- TABLA: vacuna
-- =============================================
CREATE TABLE vacuna
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    mascota_id UUID NOT NULL,
    tipo VARCHAR(255) NOT NULL,
    fecha_aplicacion DATE NOT NULL,
    meses_vigencia INTEGER NOT NULL DEFAULT 12 CHECK (meses_vigencia > 0),
    fecha_vencimiento DATE,
    proxima_dosis DATE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_vacuna_mascota
        FOREIGN KEY (mascota_id) REFERENCES mascota(id) ON DELETE CASCADE
);

CREATE INDEX idx_vacuna_mascota ON vacuna(mascota_id);
CREATE INDEX idx_vacuna_fecha_vencimiento ON vacuna(fecha_vencimiento);
CREATE INDEX idx_vacuna_proxima_dosis ON vacuna(proxima_dosis);

-- =============================================
-- TABLA: auditoria
-- =============================================
CREATE TABLE auditoria
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    usuario_id UUID NULL,
    accion VARCHAR(100) NOT NULL,
    entidad VARCHAR(100) NOT NULL,
    ip_address VARCHAR(45),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_auditoria_usuario
        FOREIGN KEY (usuario_id) REFERENCES usuario(id) ON DELETE SET NULL
);

CREATE INDEX idx_auditoria_usuario ON auditoria(usuario_id);
CREATE INDEX idx_auditoria_entidad ON auditoria(entidad);
CREATE INDEX idx_auditoria_created_at ON auditoria(created_at DESC);
CREATE INDEX idx_auditoria_accion ON auditoria(accion);

-- =============================================
-- TRIGGERS: Auto-update de updated_at
-- =============================================
CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER tr_usuario_updated_at
    BEFORE UPDATE ON usuario
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER tr_mascota_updated_at
    BEFORE UPDATE ON mascota
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
