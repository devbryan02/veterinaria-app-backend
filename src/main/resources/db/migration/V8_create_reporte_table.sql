CREATE TABLE reporte
(
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    fecha DATE NOT NULL,
    descripcion VARCHAR(255),
    data JSON,
    admin_id UUID NOT NULL,
    CONSTRAINT fk_admin_reporte FOREIGN KEY (admin_id) REFERENCES admin(id)
);
