# Endpoints - API Backend (Admin)

Documentación clara y centralizada de todos los endpoints del Admin.

---

## Base Path

```
/admin
```

---

# 1. Dueños (`/admin/dueno`)

### Obtener todos los dueños

```
GET /admin/dueno
```

### Crear dueño

```
POST /admin/dueno
```

### Obtener dueño por ID

```
GET /admin/dueno/{duenoId}
```

### Obtener Detalle completo del dueño

```
GET /admin/dueno/details/{duenoId}
```

### Actualizar dueño

```
PUT /admin/dueno/{id}
```

### Eliminar dueño

```
DELETE /admin/dueno/{id}
```

### Buscar dueños (search)

```
GET /admin/dueno/search
```

### Obtener mascotas por dueño

```
GET /admin/dueno/{duenoId}/mascotas
```

---

# 2. Mascotas (`/admin/mascota`)

### Listar mascotas
Devuelve un listado limitado para uso administrativo.  
No está paginado, pero está limitado para evitar respuestas grandes.
Usa filtros y búsquedas para obtener resultados específicos.

```
GET /admin/mascota
```

### Crear mascota

```
POST /admin/mascota
```

### Obtener mascota por ID

```
GET /admin/mascota/{id}
```

### Actualizar mascota

```
PUT /admin/mascota/{id}
```

### Eliminar mascota

```
DELETE /admin/mascota/{id}
```

### Buscar mascotas (search)

```
GET /admin/mascota/search
```

### Filtrar mascotas

```
GET /admin/mascota/filter
```

### Detalle completo de una mascota

```
GET /admin/mascota/details/{mascotaId}
```

---

# 3. Vacunas (`/admin/vacuna`)

### Listar vacunas

```
GET /admin/vacuna
```

### Crear vacuna

```
POST /admin/vacuna
```

### Obtener vacuna por ID

```
GET /admin/vacuna/{id}
```

### Eliminar vacuna

```
DELETE /admin/vacuna/{id}
```

### Filtrar vacunas

```
GET /admin/vacuna/filter
```

### Buscar por rango de fechas

```
GET /admin/vacuna/date-range
```

---

# 4. Reportes (`/admin/reporte`)

### Exportar reporte por dueño

```
GET /admin/reporte/dueno/{duenoId}
```

### Exportar todo

```
GET /admin/reporte/todos
```

---

# 5. Stats del sistema (`/admin/stats`)

### Estadísticas generales

```
GET /admin/stats/overview
```

---

Documento actualizado hoy 20 de noviembre
