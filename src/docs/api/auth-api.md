# AuthController

## Descripción
Controlador REST que gestiona las operaciones de autenticación y autorización de usuarios.

## Ruta Base
`/auth`

## Dependencias
- `AuthenticationService`: Servicio de autenticación

## Endpoints

### POST /auth/login
Autentica un usuario en el sistema.

**Request Body**: `LoginRequest`
```json
{
  "correo": "usuario@example.com",
  "password": "contraseña123"
}
```

**Validaciones**:
- correo: obligatorio, formato email válido
- password: obligatorio, mínimo 6 caracteres

**Response**: `AuthResponse`
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.. .",
  "refreshToken": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.. .",
  "tokenType": "Bearer",
  "expiresAt": "2025-11-27T15:30:00",
  "user": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "nombre": "Juan Pérez",
    "correo": "usuario@example.com",
    "roles": ["ROLE_USER"]
  }
}
```

### POST /auth/register
Registra un nuevo usuario en el sistema.

**Request Body**: `RegisterRequest`
```json
{
  "nombre": "Juan Pérez",
  "correo": "usuario@example. com",
  "password": "Password123! ",
  "telefono": "987654321",
  "dni": "12345678",
  "direccion": "Av. Principal 123, Lima"
}
```

**Validaciones**:
- nombre: obligatorio
- correo: obligatorio, formato email válido
- password: obligatorio, 8-50 caracteres, debe contener mayúscula, minúscula, número y carácter especial
- telefono: obligatorio, 9 dígitos comenzando con 9
- dni: obligatorio, exactamente 8 dígitos
- direccion: obligatorio, 5-200 caracteres

**Response**: `AuthResponse` (mismo formato que login)

**Status**: 201 CREATED

### POST /auth/refresh
Renueva el token de autenticación.

**Headers**:
- `Authorization`: Bearer {token}

**Response**: `AuthResponse` (mismo formato que login)

### GET /auth/me
Obtiene la información del usuario autenticado.

**Headers**:
- `Authorization`: Bearer {token}

**Response**: Información del usuario actual
```json
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "nombre": "Juan Pérez",
  "correo": "usuario@example.com",
  "roles": ["ROLE_USER"]
}
```

## Características
- Programación reactiva con Reactor (Mono)
- Validación de datos con Jakarta Validation
- Logging con SLF4J
- Tokens JWT para autenticación