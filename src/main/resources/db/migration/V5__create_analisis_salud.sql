CREATE TABLE analisis_salud
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    fecha DATE NOT NULL,
    resultado JSON,
    confianza INTEGER,
    observacion VARCHAR(255),
    mascota_id UUID NOT NULL,
    admin_id UUID NOT NULL,
    CONSTRAINT fk_mascota_analisis FOREIGN KEY (mascota_id) REFERENCES mascota(id),
    CONSTRAINT fk_admin_analisis FOREIGN KEY (admin_id) REFERENCES admin(id)
);
