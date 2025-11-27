# ReporteController

## Descripción
Controlador REST que gestiona la generación y exportación de reportes de vacunación en formato Excel.

## Ruta Base
`/veterinaria/reporte`

## Dependencias
- `ExportService`: Servicio para exportar datos a Excel
- `GetDuenoService`: Servicio para consultar información de dueños

## Endpoints

### GET /veterinaria/reporte/dueno/{id}
Exporta el registro de vacunación de todas las mascotas de un dueño específico.

**Path Parameter**: `id` (UUID del dueño)

**Response**: Archivo Excel (. xlsx)
- **Content-Type**: `application/octet-stream`
- **Content-Disposition**: `attachment; filename=registro_vacunacion_dueno_{nombre}. xlsx`

**Ejemplo**: `/veterinaria/reporte/dueno/550e8400-e29b-41d4-a716-446655440000`

**Descripción del archivo**:
El archivo Excel contiene el historial completo de vacunación de todas las mascotas asociadas al dueño, incluyendo:
- Información del dueño
- Lista de mascotas
- Registro de vacunas aplicadas por mascota
- Fechas de aplicación y vencimiento
- Próximas dosis

**Status**: 200 OK

### GET /veterinaria/reporte/todos
Exporta el registro completo de vacunación de todos los dueños y mascotas filtrado por año.

**Query Parameter**: `anio` (int) - Año del reporte

**Ejemplo**: `/veterinaria/reporte/todos?anio=2025`

**Response**: Archivo Excel (.xlsx)
- **Content-Type**: `application/octet-stream`
- **Content-Disposition**: `attachment; filename=registro_vacunacion_completo_{anio}.xlsx`

**Descripción del archivo**:
El archivo Excel contiene el registro completo de vacunación del año especificado, incluyendo:
- Todos los dueños registrados
- Todas las mascotas del sistema
- Historial completo de vacunación del año
- Estadísticas generales
- Resumen por tipo de vacuna

**Status**: 200 OK

## Características
- Programación reactiva con Reactor (Mono)
- Generación dinámica de archivos Excel
- Descarga directa de archivos
- Filtrado por dueño y año
- Nombres de archivo personalizados
- Manejo de streaming de datos binarios