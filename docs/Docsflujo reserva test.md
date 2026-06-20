# 📋 Módulo de Reservas - MyHotelControl

## Flujo General

```
POST /reservas
│
├── 1. Validaciones
│ ├── ¿Existe el huésped?
│ └── ¿Existe la habitación?
│
├── 2. Cálculos
│ ├── fechaSalida = fechaEntrada + cantidadNoches
│ ├── montoTotal = precioPorNoche * cantidadNoches
│ ├── montoAnticipo = montoTotal * 30%
│ └── fechaLimite = now() + 24h
│
├── 3. Guardar Reserva (estado: PENDIENTE)
│
├── 4. Generar Disponibilidad Diaria
│ ├── Valida habitación libre en el rango
│ ├── dia 1 → valida → guarda
│ ├── dia 2 → valida → guarda
│ └── dia N → valida → guarda
│
└── 5. Retorna DTO con datos de la reserva
```
text


---

## Estados de la Reserva

| Estado | Descripción | Siguiente Estado |
|--------|-------------|-----------------|
| `PENDIENTE` | Reserva creada, sin depósito | `CONFIRMADO` / `CANCELADO` |
| `CONFIRMADO` | Depósito pagado | `EN_USO` / `CANCELADO` |
| `EN_USO` | Cliente hizo check-in | `COMPLETADO` |
| `COMPLETADO` | Cliente hizo check-out | — |
| `CANCELADO` | Reserva cancelada | — |

---

## Ciclo de Vida
```
PENDIENTE ──── paga depósito ────▶ CONFIRMADO
│ │
│ check-in
│ │
▼ ▼
CANCELADO ◀── tiempo vence ──── EN_USO
▲ │
│ check-out
└──── cliente cancela ──── │
▼
COMPLETADO
```

text

---

## Lógica de Disponibilidad Diaria
```
Reserva: Habitación 101 | Entrada: 01/03 | 4 noches
↓
┌─────────────────────────────────────────┐
│ DISPONIBILIDAD │
├──────────┬─────────────┬────────────────┤
│ fecha │ habitación │ estado │
├──────────┼─────────────┼────────────────┤
│ 01/03 │ 101 │ PENDIENTE │
│ 02/03 │ 101 │ PENDIENTE │
│ 03/03 │ 101 │ PENDIENTE │
│ 04/03 │ 101 │ PENDIENTE │
└──────────┴─────────────┴────────────────┘
4 registros = 4 noches
```

---

## Reglas de Negocio

| Regla | Detalle |
|-------|---------|
| ⏰ Tiempo límite | 24 horas para pagar depósito |
| 💰 Depósito | 30% del monto total |
| 🔒 Unicidad | Una habitación no puede tener dos reservas el mismo día |
| 🗑️ Soft Delete | Nunca se eliminan registros físicamente |
| 🔄 Transacción | Reserva + Disponibilidad se guardan juntos o no se guarda nada |

---

## Cálculos
fechaSalida = fechaEntrada + cantidadNoches
montoTotal = precioPorNoche × cantidadNoches
montoAnticipo = montoTotal × 0.30
fechaLimiteConfirm = fechaCreacion + 24 horas

text

---

## Datos de Entrada

```
json
{
  "huespedId": "UUID",
  "habitacionId": "UUID",
  "fechaEntrada": "2024-03-01",
  "cantidadNoches": 4
}
Datos de Salida
json
{
  "id": "UUID",
  "nombreHuesped": "Pedro Dominguez",
  "numeroHabitacion": "101",
  "fechaEntrada": "2024-03-01",
  "fechaSalida": "2024-03-05",
  "cantidadNoches": 4,
  "precioPorNoche": 100.00,
  "montoAnticipo": 120.00,
  "montoTotal": 400.00,
  "estado": "PENDIENTE",
  "fechaLimiteConfirmacion": "2024-03-01T12:00:00"
}
```
```
Responsabilidades

Clase	Responsabilidad
ReservaServices	Orquesta el flujo completo
DisponibilidadService	Genera los registros diarios
HuespedValidacionesHelper	Valida existencia del huésped
HabitacionValidadorHelper	Valida habitación y trae precio
DisponibilidadValidadorHelper	Valida disponibilidad por día
ReservaMapper	Convierte DTOs a entidades
Consultas Clave
Necesidad	Entidad
¿Cuántas noches reservó Pedro?	Reserva.cantidadNoches
¿Cuándo entra Pedro?	Reserva.fechaEntrada
¿Cuánto debe Pedro?	Reserva.montoTotal
¿Está libre hab 101 hoy?	Disponibilidad.existsByFechaAndHabitacionId
¿Qué habitaciones libres hay hoy?	Habitaciones NOT IN Disponibilidad
¿Ocupación del hotel en marzo?	Disponibilidad.findByFechaBetween
```