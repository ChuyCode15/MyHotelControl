aJUSTE DE LA ENTIDA PRINCIPAL DE DISPONIBILADA BASADA EN LA PLANECION DE ACONTROL DE FLUJO A TRAVEZ DEL CONTROL DE FLUO DE RESERVAS DIARIAS 

```
Reserva.java
│
├── 🔑 IDENTIFICACIÓN
│     ├── id                      ← UUID autogenerado
│     │
│     ├── huespedId               ← De quién es la reserva
│     └── nombreHuesped           ← Sin JOIN para mostrar nombre
│
├── 🏠 HABITACIÓN - Denormalizado
│     ├── habitacionId            ← Qué habitación
│     └── numeroHabitacion        ← Sin JOIN para mostrar número
│
├── 📅 FECHAS
│     ├── fechaEntrada            ← Cuándo llega
│     ├── fechaSalida             ← Cuándo se va
│     └── cantidadNoches          ← Sin tocar Disponibilidad ⚡
│
├── 💰 MONTOS
│     ├── precioPorNoche          ← Base del cálculo
│     ├── montoAnticipo           ← Depósito requerido
│     └── montoTotal              ← Respuesta directa ⚡
│
├── 🔗 RELACIONES
│     └── List<Disponibilidad>    ← Sus días en el calendario
│           └── @OneToMany ✅
│
├── 📊 ESTADO
│     └── @Enumerated(STRING)     ← PENDIENTE por default ✅
│
├── ⏰ CONTROL DE TIEMPO
│     └── fechaLimiteConfirmacion ← Auto-cancelación
│
├── 📋 AUDITORÍA
│     └── fechaCreacion           ← updatable = false ✅
│
└── 🗑️ SOFT DELETE
└── activo = true           ← Nunca borrar físico ✅

```
---
FUCIONAMINTO BASICO DEL FLUJO DEL SERVICO DE DISPONIBILIDAD 

```

POST /reservas
│
▼
registrarReservaNueva(datos)
│
├── 1. VALIDACIONES
│         │
│         ├── ¿Existe el huésped?     → ❌ Excepción
│         └── ¿Existe la habitación?  → ❌ Excepción
│
├── 2. CREAR RESERVA
│         │
│         ├── Mapper convierte DTO → Entidad
│         └── save(reserva) → Ya tiene UUID ✅
│
├── 3. GENERAR DISPONIBILIDAD
│         │
│         └── generarRegistroDisponibilidad(datos, reserva.getId())
│                   │
│                   ├── ¿Habitación libre en esas fechas? → ❌ Excepción
│                   │
│                   ├── dia 1 → valida → construye → save ✅
│                   ├── dia 2 → valida → construye → save ✅
│                   ├── dia 3 → valida → construye → save ✅
│                   └── dia 4 → valida → construye → save ✅
│
└── 4. RETORNA
│
└── DTO con datos de la reserva ✅

```
---

```


CLIENTE ENVÍA                   SERVICE CALCULA
──────────────                  ───────────────
huespedId          →            nombreHuesped (Helper)
habitacionId       →            numeroHabitacion (Helper)
fechaEntrada       →            fechaSalida (+noches)
cantidadNoches     →            montoTotal (precio * noches)
→            precioPorNoche (de Habitacion)
→            montoAnticipo (total * 30%)
→            fechaLimiteConfirmacion (+24h)
→            estado = PENDIENTE

```