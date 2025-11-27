# DuenoController

## Descripción
Controlador REST que gestiona las operaciones CRUD de dueños de mascotas en el sistema veterinario.

## Ruta Base
`/veterinaria/dueno`

## Dependencias
- `CreateDuenoService`: Servicio para crear dueños
- `GetDuenoService`: Servicio para consultar dueños
- `UpdateDuenoService`: Servicio para actualizar dueños
- `DeleteDuenoService`: Servicio para eliminar dueños
- `GetDuenoDetailsService`: Servicio para obtener detalles completos

## Endpoints

### POST /veterinaria/dueno
Crea un nuevo dueño en el sistema.

**Request Body**: `DuenoNewRequest`
```json
{
  "nombre": "Carlos Mendoza",
  "dni": "12345678",
  "direccion": "Av. Los Pinos 456, Lima",
  "telefono": "987654321",
  "correo": "carlos@example.com",
  "password": "password123",
  "latitud": "-12.0464",
  "longitud": "-77.0428"
}
```

**Validaciones**:
- nombre: obligatorio, 2-100 caracteres
- dni: obligatorio, exactamente 8 dígitos
- direccion: obligatorio, 5-200 caracteres
- telefono: obligatorio, 9 dígitos comenzando con 9
- correo: obligatorio, formato email válido, máximo 100 caracteres
- password: obligatorio, 8-50 caracteres
- latitud: obligatorio
- longitud: obligatorio

**Response**: `OperationResponseStatus`
```json
{
  "success": true,
  "message": "Dueño creado exitosamente"
}
```

**Status**: 201 CREATED

### GET /veterinaria/dueno/details/{duenoId}
Obtiene los detalles completos de un dueño incluyendo sus mascotas.

**Path Parameter**: `duenoId` (UUID)

**Response**: `DuenoFullDetails`
```json
{
  "nombre": "Carlos Mendoza",
  "dni": "12345678",
  "direccion": "Av. Los Pinos 456, Lima",
  "telefono": "987654321",
  "correo": "carlos@example. com",
  "longitud": "-77.0428",
  "latitud": "-12.0464",
  "mascota": {
  }
}
```

### GET /veterinaria/dueno
Obtiene la lista de todos los dueños con la cantidad de mascotas.

**Response**: Lista de `DuenoWithCantMascotaDetails`
```json
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "nombre": "Carlos Mendoza",
    "dni": "12345678",
    "direccion": "Av. Los Pinos 456, Lima",
    "telefono": "987654321",
    "correo": "carlos@example.com",
    "cantidadmascotas": 3
  }
]
```

### GET /veterinaria/dueno/search
Busca dueños por término de búsqueda.

**Query Parameter**: `term` (String)

**Ejemplo**: `/veterinaria/dueno/search?term=Carlos`

**Response**: Lista de `DuenoWithCantMascotaDetails` (mismo formato que GET all)

### GET /veterinaria/dueno/{id}
Obtiene un dueño específico por su ID.

**Path Parameter**: `id` (UUID)

**Response**: `DuenoWithCantMascotaDetails`
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "nombre": "Carlos Mendoza",
  "dni": "12345678",
  "direccion": "Av. Los Pinos 456, Lima",
  "telefono": "987654321",
  "correo": "carlos@example.com",
  "cantidadmascotas": 3
}
```

### PUT /veterinaria/dueno/{id}
Actualiza la información de un dueño existente.

**Path Parameter**: `id` (UUID)

**Request Body**: `DuenoUpdateRequest`
```json
{
  "nombre": "Carlos Mendoza Actualizado",
  "correo": "carlos. nuevo@example.com",
  "telefono": "987654322",
  "dni": "12345678",
  "direccion": "Nueva Av. Los Pinos 789, Lima"
}
```

**Validaciones**:
- nombre: obligatorio, 2-100 caracteres
- correo: obligatorio
- telefono: obligatorio, 9 dígitos comenzando con 9
- dni: obligatorio, exactamente 8 dígitos
- direccion: obligatorio, 5-200 caracteres

**Response**: `OperationResponseStatus`
```json
{
  "success": true,
  "message": "Dueño actualizado exitosamente"
}
```

### DELETE /veterinaria/dueno/{id}
Elimina un dueño del sistema.

**Path Parameter**: `id` (UUID)

**Response**: `OperationResponseStatus`
```json
{
  "success": true,
  "message": "Dueño eliminado exitosamente"
}
```

## Características
- Programación reactiva con Reactor (Mono/Flux)
- Validación de datos con Jakarta Validation
- Soporte para geolocalización (latitud/longitud)
- Búsqueda y filtrado de dueños
- Relación con mascotas