# DashboardController

## Descripción
Controlador REST que proporciona estadísticas y métricas del sistema veterinario para el dashboard.

## Ruta Base
`/veterinaria/stats`

## Dependencias
- `DashboardStatsService`: Servicio para obtener estadísticas del sistema

## Endpoints

### GET /veterinaria/stats/overview
Obtiene un resumen completo de las estadísticas del sistema.

**Response**: `DashboardStatsResponse`
```json
{
  "totalDuenos": 150,
  "totalMascotas": 320,
  "totalVacunas": 1250,
  "mascotasPorEspecie": {
    "perros": 180,
    "gatos": 120,
    "otros": 20
  },
  "vacunasPorMes": [
    {
      "mes": "Enero",
      "cantidad": 95
    },
    {
      "mes": "Febrero",
      "cantidad": 110
    }
  ],
  "mascotasRegistradasPorAnio": [
    {
      "anio": 2024,
      "cantidad": 180
    },
    {
      "anio": 2025,
      "cantidad": 140
    }
  ]
}
```

**Campos de Response**:
- `totalDuenos`: Número total de dueños registrados
- `totalMascotas`: Número total de mascotas registradas
- `totalVacunas`: Número total de vacunas aplicadas
- `mascotasPorEspecie`: Distribución de mascotas por especie
- `vacunasPorMes`: Cantidad de vacunas aplicadas por mes
- `mascotasRegistradasPorAnio`: Cantidad de mascotas registradas por año

## Características
- Estadísticas en tiempo real
- Programación reactiva con Reactor (Mono)
- Datos agregados para visualización en dashboard
- Métricas de dueños, mascotas y vacunas