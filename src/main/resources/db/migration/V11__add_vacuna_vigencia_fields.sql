-- Agregar campos para control de vigencia de vacunas
ALTER TABLE vacuna
    ADD COLUMN meses_vigencia INTEGER DEFAULT 12 NOT NULL,
    ADD COLUMN fecha_vencimiento DATE,
    ADD COLUMN proxima_dosis DATE;

-- Calcular fecha_vencimiento para las vacunas existentes
UPDATE vacuna
SET fecha_vencimiento = fecha_aplicacion + INTERVAL '12 months'
WHERE fecha_vencimiento IS NULL;

-- Crear índices para mejorar performance en las consultas de filtrado
CREATE INDEX idx_vacuna_mascota_id ON vacuna(mascota_id);
CREATE INDEX idx_vacuna_fecha_aplicacion ON vacuna(fecha_aplicacion);
CREATE INDEX idx_vacuna_fecha_vencimiento ON vacuna(fecha_vencimiento);
CREATE INDEX idx_vacuna_tipo ON vacuna(tipo);

-- Comentarios para documentar
COMMENT ON COLUMN vacuna.meses_vigencia IS 'Duración de vigencia de la vacuna en meses. Default: 12 meses';
COMMENT ON COLUMN vacuna.fecha_vencimiento IS 'Fecha calculada de vencimiento = fecha_aplicacion + meses_vigencia';
COMMENT ON COLUMN vacuna.proxima_dosis IS 'Fecha programada para refuerzo o próxima dosis (opcional)';