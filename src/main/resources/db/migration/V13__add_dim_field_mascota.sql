-- Crear secuencia para el contador
CREATE SEQUENCE IF NOT EXISTS mascota_identificador_seq START WITH 1 INCREMENT BY 1;

-- Agregar la columna identificador
ALTER TABLE mascota
    ADD COLUMN IF NOT EXISTS identificador VARCHAR(30) UNIQUE;

-- Crear la función generadora
CREATE OR REPLACE FUNCTION generar_identificador_mascota()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.identificador := 'PET-MAACD-' || nextval('mascota_identificador_seq');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Crear el trigger asociado a la tabla
DO $$
    BEGIN
        IF NOT EXISTS (
            SELECT 1 FROM pg_trigger WHERE tgname = 'mascota_identificador_trigger'
        ) THEN
            CREATE TRIGGER mascota_identificador_trigger
                BEFORE INSERT ON mascota
                FOR EACH ROW
            EXECUTE FUNCTION generar_identificador_mascota();
        END IF;
    END;
$$;
