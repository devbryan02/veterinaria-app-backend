# ImagenController

## Descripción
Controlador REST que gestiona la carga de imágenes de mascotas en el sistema.

## Ruta Base
`/veterinaria/mascota/imagen`

## Dependencias
- `CreateImagenService`: Servicio para crear y almacenar imágenes

## Endpoints

### POST /veterinaria/mascota/imagen/upload
Sube una imagen asociada a una mascota.

**Content-Type**: `multipart/form-data`

**Request Parts**:
- `file`: Archivo de imagen (FilePart)
- `descripcion`: Descripción de la imagen (String)
- `mascotaId`: ID de la mascota (String UUID)

**Ejemplo de Request (form-data)**:
```
file: [archivo de imagen]
descripcion: "Foto de perfil"
mascotaId: "660e8400-e29b-41d4-a716-446655440000"
```

**Validaciones**:
- mascotaId: obligatorio
- descripcion: obligatorio

**Response**: `OperationResponseStatus`
```json
{
  "success": true,
  "message": "Imagen subida exitosamente"
}
```

## Características
- Programación reactiva con Reactor (Mono)
- Soporte para multipart/form-data
- Carga de archivos con Spring WebFlux
- Asociación de imágenes a mascotas