# Veterinaria API Backend

API REST para gestión de clínicas veterinarias construida con Spring Boot 3, Spring WebFlux y arquitectura hexagonal.

## Tabla de Contenidos

- [Tecnologías](#tecnologías)
- [Requisitos Previos](#requisitos-previos)
- [Instalación](#instalación)
- [Estructura del Proyecto](#estructura-del-proyecto)
- [Arquitectura](#arquitectura)
- [Base de Datos](#base-de-datos)
- [Seguridad](#seguridad)
- [Documentación API](#documentación-api)

## Tecnologías

### Core
- **Spring Boot 3** - Framework principal
- **Spring WebFlux** - Programación reactiva
- **R2DBC** - Acceso reactivo a base de datos
- **Java 17+** - Lenguaje de programación

### Seguridad
- **Spring Security** - Autenticación y autorización
- **JWT** - JSON Web Tokens para sesiones
- **BCrypt** - Encriptación de contraseñas

### Base de Datos
- **PostgreSQL** - Base de datos relacional
- **Flyway** - Migraciones de base de datos
- **R2DBC PostgreSQL** - Driver reactivo

### Utilidades
- **Apache POI** - Generación de archivos Excel
- **Lombok** - Reducción de código boilerplate
- **MapStruct** (opcional) - Mapeo de objetos

## Requisitos Previos

- **JDK**: 17 o superior
- **Maven**: 3.8+ o usa el wrapper incluido (`./mvnw`)
- **PostgreSQL**: 14+
- **Git**: Para control de versiones

## Instalación

### 1. Clonar el repositorio

```bash
git clone [URL_DEL_REPOSITORIO]
cd veterinaria-app-backend
```

### 2. Configurar base de datos

Crea una base de datos PostgreSQL:

```sql
CREATE DATABASE veterinaria_db;
```

### 3. Configurar variables de entorno

Edita `src/main/resources/application.yml` o crea variables de entorno:

```yaml
spring:
  r2dbc:
    url: r2dbc:postgresql://localhost:5432/veterinaria_db
    username: tu_usuario
    password: tu_password
```

### 4. Compilar y ejecutar

```bash
# Con Maven wrapper (recomendado)
./mvnw clean install
./mvnw spring-boot:run

# O con Maven instalado
mvn clean install
mvn spring-boot:run
```

La API estará disponible en `http://localhost:8080`

## Estructura del Proyecto

El proyecto sigue **Arquitectura Hexagonal** (Ports & Adapters):

```
src/main/java/com/app/veterinaria/
├── application/              # Capa de Aplicación
│   ├── mapper/              # Mappers request/response
│   │   ├── request/
│   │   └── response/
│   ├── repository/          # Ports (interfaces) de queries
│   └── service/             # Casos de uso
│       ├── auth/           # Autenticación
│       ├── dueno/          # Gestión de dueños
│       ├── mascota/        # Gestión de mascotas
│       ├── vacuna/         # Gestión de vacunas
│       ├── reportes/       # Generación de reportes
│       └── stats/          # Estadísticas
│
├── domain/                  # Capa de Dominio (Core)
│   ├── emuns/              # Enumeraciones
│   ├── model/              # Entidades de dominio
│   ├── repository/         # Ports (interfaces) de repositorios
│   └── valueobject/        # Value Objects
│
├── infrastructure/          # Capa de Infraestructura (Adapters)
│   ├── audit/              # Sistema de auditoría (AOP)
│   ├── config/             # Configuraciones
│   ├── mapper/             # Mappers entity <-> domain
│   ├── persistence/        # Adaptador de persistencia
│   │   ├── adapter/
│   │   │   ├── command/   # Write operations
│   │   │   ├── query/     # Read operations (CQRS)
│   │   │   └── r2dbc/     # Repositorios R2DBC
│   │   └── entity/        # Entidades JPA
│   └── web/               # Adaptador REST
│       ├── controller/
│       │   ├── admin/
│       │   ├── auth/
│       │   └── veterinaria/
│       └── dto/
│           ├── details/
│           ├── request/
│           └── response/
│
├── shared/                  # Código compartido
│   ├── config/             # Configuraciones globales (CORS, R2DBC)
│   ├── exception/          # Excepciones personalizadas
│   └── security/           # Configuración de seguridad
│       ├── config/
│       ├── jwt/            # JWT Provider y filtros
│       └── userdetails/
│
└── VeterinariaAppApplication.java
```

## Arquitectura

### Hexagonal Architecture (Clean Architecture)

La aplicación se organiza en 3 capas principales:

**1. Domain (Núcleo)**
- Entidades de negocio puras
- Interfaces de repositorio (ports)
- Value Objects
- Sin dependencias externas

**2. Application (Casos de Uso)**
- Servicios de aplicación
- Orquestación de lógica de negocio
- Interfaces de queries (CQRS)

**3. Infrastructure (Adaptadores)**
- Implementaciones de repositorios (R2DBC)
- Controllers REST
- Configuración de frameworks
- Adaptadores externos

### CQRS Pattern

Separación de operaciones de lectura y escritura:

- **Commands**: `adapter/command/` - Operaciones de escritura
- **Queries**: `adapter/query/` - Operaciones de lectura optimizadas

### Programación Reactiva

Uso de Spring WebFlux y R2DBC para operaciones no bloqueantes:

```java
public Mono<Mascota> save(Mascota mascota);
public Flux<MascotaDetails> findAll();
```

## Base de Datos

### Migraciones

Las migraciones se gestionan con Flyway en `src/main/resources/db/migration/`:

```
V1__init_schema.sql          # Schema inicial
V2__generate_dim_mascota.sql # Funciones adicionales
```

### Auditoría

Sistema de auditoría automática con AOP:

- Registra todas las operaciones CRUD
- Almacena usuario, acción, entidad y timestamp
- Usa la anotación `@Auditable`

```java
@Auditable(accion = AccionEnum.CREATE, entidad = EntityEnum.MASCOTA)
public Mono<Mascota> create(MascotaDataCreate data) { ... }
```

## Seguridad

### Autenticación JWT

- Login en `/api/auth/login`
- Token JWT con expiración configurable
- Refresh token para renovación

### Roles y Permisos

- **ADMIN**: Gestión de veterinarios
- **VET**: Gestión de mascotas, dueños y vacunas

### Configuración

En `application.yml`:

```yaml
jwt:
  secret: tu_secret_key
  expiration: 86400000  # 24 horas en ms
```

## Documentación API

La documentación completa de endpoints está en `src/docs/api/`:

- `auth-api.md` - Autenticación
- `dashboard-api.md` - Estadísticas
- `dueno-api.md` - Gestión de dueños
- `mascota-api.md` - Gestión de mascotas
- `vacuna-api.md` - Gestión de vacunas
- `imagen-mascota-api.md` - Imágenes de mascotas
- `reporte-api.md` - Exportación de datos

### Endpoints Principales

```
POST   /api/auth/login              # Login
POST   /api/auth/register           # Registro
GET    /api/dashboard/stats         # Estadísticas

GET    /api/duenos                  # Listar dueños
POST   /api/duenos                  # Crear dueño
GET    /api/duenos/{id}             # Detalle dueño
PUT    /api/duenos/{id}             # Actualizar dueño
DELETE /api/duenos/{id}             # Eliminar dueño

GET    /api/mascotas                # Listar mascotas
POST   /api/mascotas                # Crear mascota
GET    /api/mascotas/{id}           # Detalle mascota
PUT    /api/mascotas/{id}           # Actualizar mascota
DELETE /api/mascotas/{id}           # Eliminar mascota

GET    /api/vacunas                 # Listar vacunas
POST   /api/vacunas                 # Crear vacuna
PUT    /api/vacunas/{id}            # Actualizar vacuna
DELETE /api/vacunas/{id}            # Eliminar vacuna

GET    /api/reportes/export         # Exportar Excel
```

## Perfiles de Ejecución

```bash
# Desarrollo
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Producción
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

Configuraciones en:
- `application-dev.yml`
- `application-prod.yml`

---

**Versión**: 1.0.0  
**Puerto por defecto**: 8080  
**Base de datos**: PostgreSQL  
**Estado**: Produccion