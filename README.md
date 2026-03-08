# Veterinaria API – Municipalidad A.A. Cáceres Dorregaray

API REST reactiva desarrollada para la gestión del control sanitario de mascotas
de la Municipalidad Andrés Avelino Cáceres Dorregaray de Ayacucho.
Construida con Spring WebFlux, Arquitectura Hexagonal y desplegada en producción.

![Java](https://img.shields.io/badge/Java_17-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot_3-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Spring WebFlux](https://img.shields.io/badge/Spring_WebFlux-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)
![Railway](https://img.shields.io/badge/Railway-0B0D0E?style=for-the-badge&logo=railway&logoColor=white)

## Contexto

Este sistema fue desarrollado para la Municipalidad A.A. Cáceres Dorregaray
con el objetivo de digitalizar y centralizar el registro sanitario de mascotas
del distrito. Permite gestionar dueños, mascotas, vacunas, imágenes y generar
reportes exportables a Excel, con control de acceso por roles.

> **Deployado en producción** con Docker + Railway

## Screenshots

[Dashboard](docs/demo.png)

## Características principales

- ✅ API REST reactiva con **15 endpoints** (mascotas, dueños, vacunas, imágenes, reportes)
- ✅ **Arquitectura Hexagonal + CQRS** — separación limpia de lectura/escritura
- ✅ Autenticación **JWT** con roles Admin y Veterinaria
- ✅ **Auditoría automática con AOP** — registra todas las operaciones CRUD
- ✅ Reportes exportables a **Excel** con Apache POI
- ✅ Migraciones automáticas con **Flyway**
- ✅ Perfiles separados **dev/prod**

## 🛠️ Stack Tecnológico

| Categoría | Tecnología |
|---|---|
| Framework | Spring Boot 3 + Spring WebFlux |
| Base de datos | PostgreSQL + R2DBC (reactivo) |
| Seguridad | Spring Security + JWT + BCrypt |
| Arquitectura | Hexagonal + CQRS |
| Auditoría | AOP con anotación @Auditable |
| Migraciones | Flyway |
| Deploy | Docker + Railway |

## Arquitectura Hexagonal
```
src/main/java/com/app/veterinaria/
├── domain/          # Núcleo — entidades, ports, value objects
├── application/     # Casos de uso — servicios, CQRS queries
├── infrastructure/  # Adaptadores — REST controllers, R2DBC, AOP
└── shared/          # Seguridad, excepciones, configuración global
```

**Flujo de datos:**
```
HTTP Request → Controller (Infrastructure)
            → Service (Application)
            → Repository Port (Domain)
            → R2DBC Adapter (Infrastructure)
            → PostgreSQL
```

## Seguridad

| Rol | Permisos |
|---|---|
| ADMIN | Gestión de veterinarios + acceso total |
| VET | Gestión de mascotas, dueños y vacunas |

Autenticación via JWT con access token + refresh token.

## Endpoints principales
```
# Auth
POST   /api/auth/login
POST   /api/auth/register

# Dashboard
GET    /api/dashboard/stats

# Dueños
GET    /api/duenos
POST   /api/duenos
GET    /api/duenos/{id}
PUT    /api/duenos/{id}
DELETE /api/duenos/{id}

# Mascotas
GET    /api/mascotas
POST   /api/mascotas
GET    /api/mascotas/{id}
PUT    /api/mascotas/{id}
DELETE /api/mascotas/{id}

# Vacunas
GET    /api/vacunas
POST   /api/vacunas
PUT    /api/vacunas/{id}
DELETE /api/vacunas/{id}

# Reportes
GET    /api/reportes/export     # Exportar Excel
```

## Instalación local

### 1. Clonar el repositorio
```bash
git clone https://github.com/devbryan02/veterinaria-app-backend
cd veterinaria-app-backend
```

### 2. Configurar base de datos
```sql
CREATE DATABASE veterinaria_db;
```

### 3. Configurar variables de entorno
```yaml
spring:
  r2dbc:
    url: r2dbc:postgresql://localhost:5432/veterinaria_db
    username: tu_usuario
    password: tu_password

jwt:
  secret: tu_secret_key
  expiration: 86400000
```

### 4. Ejecutar
```bash
# Desarrollo
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Producción
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

## Docker
```bash
docker build -t veterinaria-api .
docker run -p 8080:8080 veterinaria-api
```

---

**Estado**: ✅ Producción | **Puerto**: 8080 | **Java**: 17+ | **DB**: PostgreSQL 14+
