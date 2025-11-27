# VacunaController

## Descripción
Controlador REST que gestiona las operaciones de registro y consulta de vacunas aplicadas a las mascotas.

## Ruta Base
`/veterinaria/vacuna`

## Dependencias
- `CreateVacunaService`: Servicio para registrar vacunas
- `GetVacunaService`: Servicio para consultar vacunas

## Endpoints

### POST /veterinaria/vacuna
Registra una nueva vacuna aplicada a una mascota.

**Request Body**: `VacunaNewRequest`
```json
{
  "mascotaId": "660e8400-e29b-41d4-a716-446655440000",
  "tipo": "Rabia",
  "fechaAplicacion": "2025-11-27",
  "mesesVigencia": 12
}
```

**Validaciones**:
- mascotaId: obligatorio
- tipo: obligatorio
- fechaAplicacion: obligatorio (formato fecha)
- mesesVigencia: obligatorio, no nulo

**Response**: `OperationResponseStatus`
```json
{
  "success": true,
  "message": "Vacuna registrada exitosamente"
}
```

**Status**: 201 CREATED

### GET /veterinaria/vacuna/{id}
Obtiene una vacuna específica por su ID.

**Path Parameter**: `id` (UUID)

**Response**: `VacunaWithMascotaDetails`
```json
{
  "id": "770e8400-e29b-41d4-a716-446655440000",
  "tipo": "Rabia",
  "fechaaplicacion": "2025-11-27",
  "mascota": "Max",
  "mesesvigencia": 12,
  "fechavencimiento": "2026-11-27",
  "proximadosis": "2026-10-27"
}
```

**Status**: 200 OK

### GET /veterinaria/vacuna
Obtiene todas las vacunas registradas.

**Response**: Lista de `VacunaWithMascotaDetails`
```json
[
  {
    "id": "770e8400-e29b-41d4-a716-446655440000",
    "tipo": "Rabia",
    "fechaaplicacion": "2025-11-27",
    "mascota": "Max",
    "mesesvigencia": 12,
    "fechavencimiento": "2026-11-27",
    "proximadosis": "2026-10-27"
  },
  {
    "id": "880e8400-e29b-41d4-a716-446655440000",
    "tipo": "Parvovirus",
    "fechaaplicacion": "2025-10-15",
    "mascota": "Luna",
    "mesesvigencia": 6,
    "fechavencimiento": "2026-04-15",
    "proximadosis": "2026-03-15"
  }
]
```

**Status**: 200 OK

### GET /veterinaria/vacuna/filter
Filtra vacunas por tipo.

**Query Parameter**: `tipo` (String)

**Ejemplo**: `/veterinaria/vacuna/filter?tipo=Rabia`

**Response**: Lista de `VacunaWithMascotaDetails` (mismo formato que GET all)

**Status**: 200 OK

### GET /veterinaria/vacuna/date-range
Obtiene vacunas aplicadas en un rango de fechas.

**Query Parameters**:
- `startDate` (LocalDate, formato ISO: YYYY-MM-DD)
- `endDate` (LocalDate, formato ISO: YYYY-MM-DD)

**Ejemplo**: `/veterinaria/vacuna/date-range?startDate=2025-01-01&endDate=2025-12-31`

**Response**: Lista de `VacunaWithMascotaDetails` (mismo formato que GET all)

**Status**: 200 OK

## Características
- Programación reactiva con Reactor (Mono/Flux)
- Validación de datos con Jakarta Validation
- Cálculo automático de fecha de vencimiento y próxima dosis
- Filtrado por tipo de vacuna
- Consulta por rangos de fechas
- Control de vigencia de vacunas