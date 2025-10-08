CREATE TABLE reporte
(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    fecha DATE NOT NULL,
    descripcion VARCHAR(255),
    data JSON,
    admin_id UUID NOT NULL,
    CONSTRAINT fk_admin_reporte FOREIGN KEY (admin_id) REFERENCES admin(id)
);
