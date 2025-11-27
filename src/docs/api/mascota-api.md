# MascotaController

## Descripción
Controlador REST que gestiona las operaciones CRUD de mascotas en el sistema veterinario.

## Ruta Base
`/veterinaria/mascota`

## Dependencias
- `CreateMascotaService`: Servicio para crear mascotas
- `GetMascotaService`: Servicio para consultar mascotas
- `UpdateMascotaService`: Servicio para actualizar mascotas
- `DeleteMascotaService`: Servicio para eliminar mascotas
- `GetMascotaDetailsService`: Servicio para obtener detalles completos

## Endpoints

### POST /veterinaria/mascota
Crea una nueva mascota en el sistema.

**Request Body**: `MascotaNewRequest`
```json
{
  "usuarioId": "550e8400-e29b-41d4-a716-446655440000",
  "nombre": "Max",
  "especie": "Perro",
  "raza": "Labrador",
  "sexo": "Macho",
  "temperamento": "Tranquilo",
  "condicionReproductiva": "Esterilizado",
  "color": "Dorado",
  "anios": 3,
  "meses": 6
}
```

**Validaciones**:
- usuarioId: obligatorio
- nombre: obligatorio
- especie: obligatorio
- raza: obligatorio
- sexo: obligatorio
- temperamento: obligatorio
- condicionReproductiva: obligatorio
- color: obligatorio
- anios: opcional
- meses: opcional, entre 0 y 11

**Response**: `OperationResponseStatus`
```json
{
  "success": true,
  "message": "Mascota creada exitosamente"
}
```

### GET /veterinaria/mascota
Obtiene todas las mascotas con limite.

**Response**: Lista de `MascotaWithDuenoDetails`
```json
[
  {
    "id": "660e8400-e29b-41d4-a716-446655440000",
    "nombre": "Max",
    "especie": "Perro",
    "raza": "Labrador",
    "edad": "3 años 6 meses",
    "sexo": "Macho",
    "temperamento": "Tranquilo",
    "condicionreproductiva": "Esterilizado",
    "color": "Dorado",
    "dueno": "Carlos Mendoza",
    "identificador": "MASC-001"
  }
]
```

### GET /veterinaria/mascota/details/{mascotaId}
Obtiene los detalles completos de una mascota incluyendo imágenes, dueño y vacunas.

**Path Parameter**: `mascotaId` (UUID)

**Response**: `MascotaFullDetails`
```json
{
  "id": "660e8400-e29b-41d4-a716-446655440000",
  "nombre": "Max",
  "especie": "Perro",
  "raza": "Labrador",
  "edad": "3 años 6 meses",
  "sexo": "Macho",
  "color": "Dorado",
  "identificador": "MASC-001",
  "fotoUrl": "https://example.com/foto.jpg",
  "estado": "ACTIVO",
  "imagenList": [
    {
      "id": "img-001",
      "url": "https://example.com/foto.jpg",
      "descripcion": "Foto principal"
    }
  ],
  "dueno": {
    "nombre": "Carlos Mendoza",
    "telefono": "987654321",
    "correo": "carlos@example.com"
  },
  "vacuna": {
    "total": 5,
    "proxima": "2025-12-01"
  }
}
```

### GET /veterinaria/mascota/search
Busca mascotas por término de búsqueda.

**Query Parameter**: `term` (String)

**Ejemplo**: `/veterinaria/mascota/search?term=Max`

**Response**: Lista de `MascotaWithDuenoDetails` (mismo formato que GET all)

### GET /veterinaria/mascota/filter
Filtra mascotas por especie, sexo y/o raza.

**Query Parameters**:
- `especie` (String, opcional)
- `sexo` (String, opcional)
- `raza` (String, opcional)

**Ejemplo**: `/veterinaria/mascota/filter?especie=Perro&sexo=Macho`

**Response**: Lista de `MascotaWithDuenoDetails` (mismo formato que GET all)

### GET /veterinaria/mascota/{id}
Obtiene una mascota específica por su ID.

**Path Parameter**: `id` (UUID)

**Response**: `MascotaWithDuenoDetails`
```json
{
  "id": "660e8400-e29b-41d4-a716-446655440000",
  "nombre": "Max",
  "especie": "Perro",
  "raza": "Labrador",
  "edad": "3 años 6 meses",
  "sexo": "Macho",
  "temperamento": "Tranquilo",
  "condicionreproductiva": "Esterilizado",
  "color": "Dorado",
  "dueno": "Carlos Mendoza",
  "identificador": "MASC-001"
}
```

### PUT /veterinaria/mascota/{id}
Actualiza la información de una mascota existente.

**Path Parameter**: `id` (UUID)

**Request Body**: `MascotaUpdateRequest`
```json
{
  "nombre": "Max Actualizado",
  "especie": "Perro",
  "raza": "Labrador Retriever",
  "sexo": "Macho",
  "temperamento": "Muy tranquilo",
  "condicionReproductiva": "Esterilizado",
  "color": "Dorado claro",
  "anios": 4,
  "meses": 0,
  "estado": "ACTIVO"
}
```

**Validaciones**:
- nombre: obligatorio
- especie: obligatorio
- raza: obligatorio
- sexo: obligatorio
- temperamento: obligatorio
- condicionReproductiva: obligatorio
- color: obligatorio
- anios: opcional
- meses: opcional, entre 0 y 11
- estado: obligatorio

**Response**: `OperationResponseStatus`
```json
{
  "success": true,
  "message": "Mascota actualizada exitosamente"
}
```

### DELETE /veterinaria/mascota/{id}
Elimina una mascota del sistema.

**Path Parameter**: `id` (UUID)

**Response**: `OperationResponseStatus`
```json
{
  "success": true,
  "message": "Mascota eliminada exitosamente"
}
```

## Características
- Programación reactiva con Reactor (Mono/Flux)
- Validación de datos con Jakarta Validation
- Búsqueda y filtrado avanzado de mascotas
- Gestión de edad en años y meses
- Relación con dueños, imágenes y vacunas
- Sistema de identificadores únicos