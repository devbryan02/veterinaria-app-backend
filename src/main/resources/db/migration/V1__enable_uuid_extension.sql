-- PostgreSQL 13+ ya tiene gen_random_uuid() nativo
-- Este archivo existe solo para mantener el versionado de migraciones
-- No requiere ninguna extensión adicional

-- Si en el futuro necesitas uuid-ossp, descomenta la siguiente línea:
-- CREATE EXTENSION IF NOT EXISTS "uuid-ossp";