

```

Disponibilidad.java
│
├── 🔑 CLAVE DEL SISTEMA
│     ├── fecha                 ← El día del calendario
│     └── habitacionId          ← La habitación
│           └── Juntos = @UniqueConstraint ✅
│
├── ⚡ RENDIMIENTO
│     ├── habitacionNumero      ← Sin JOIN para mostrar número
│     ├── fechaSalidaReserva    ← Sin JOIN para saber cuándo se libera
│     ├── precioDia             ← Sin JOIN para saber el precio
│     ├── nocheNumero           ← "Noche X..."
│     └── totalNoches           ← "...de N"
│
├── 🔗 RELACIONES
│     └── reservaId             ← De quién es el día
│
├── 📊 ESTADO
│     └── @Enumerated(STRING)   ← Legible en BD ✅
│
└── 🗑️ SOFT DELETE
└── activo = true         ← Nunca borrar físico ✅

```